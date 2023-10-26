package com.example.challengedevonion.controller;

import com.example.challengedevonion.model.Boleto;
import com.example.challengedevonion.model.Status;
import com.example.challengedevonion.repository.BoletoRepository;
import com.example.challengedevonion.repository.PessoaRepository;
import com.example.challengedevonion.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/boleto")
public class BoletoController {

    @Autowired BoletoRepository repository;
    @Autowired PessoaRepository pessoaRepository;

    @GetMapping("/list/pessoa/{id}")
    public List<Boleto> listBoletoPessoa(@PathVariable Integer id){
        var pessoas = pessoaRepository.findById(id);
        return this.repository
                .findBoletosByPessoaAndStatus(pessoas.get().getId(), Status.PENDENTE);
    }

    @GetMapping("/list")
    public List<Boleto> list(){
        return repository.findAll();
    }

//    Estou transformando o Date em Calendar, por que tem o método que preciso
    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    @PostMapping("/create")
    public ResponseEntity criar(@RequestBody Boleto boleto){
        System.out.println(boleto);
//        Estou Setando a Data de vencimento para um Mês
        boleto.setDataVencimento(new Date());
        var vencimento = toCalendar(boleto.getDataVencimento());
        vencimento.add(Calendar.MONTH, 1);
        boleto.setDataVencimento(vencimento.getTime());

//        Estou verificado se a pessoa existe
        var pessoa = pessoaRepository.findById(boleto.getPessoa()).orElse(null);
        BigDecimal saldoDevedorTotalDoCliente = new BigDecimal("0");
        if (pessoa == null){
            return ResponseEntity
                    .badRequest()
                    .body("Pessoa Inexistente para este boleto, verifique e tente novamente!");
        }

//        Estou Buscando os boletos pendentes da pessoa e verificando se o valor de limite foi ultrapassado
        var boletosPendentesDaPessoa = repository
                .findBoletosByPessoaAndStatus(pessoa.getId(), Status.PENDENTE);

//        Estou somando os valores pra ver como está o limite
        for (Boleto bol: boletosPendentesDaPessoa){
            saldoDevedorTotalDoCliente = bol.getValor().add(saldoDevedorTotalDoCliente);
        }
        if (saldoDevedorTotalDoCliente.compareTo(pessoa.getValorLimiteBoletos()) >= 0){
            return ResponseEntity
                    .badRequest()
                    .body("Não é possível gerar este boleto, limite do cliente alcançado!");
        }

//        Setando o Status antes de salvar
        boleto.setStatus(Status.PENDENTE);

//        Salvando e retornando os dados
        repository.save(boleto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Boleto salvo com success!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity alter(@RequestBody Boleto boleto, @PathVariable Integer id){
        var boletoTeste = this.repository.findById(id).orElse(null);
        if (boletoTeste == null){
            return ResponseEntity
                    .badRequest()
                    .body("Boleto inexistente, verifique e tente novamente!");
        }
        Utils.copyNonNullProperties(boletoTeste, boleto);
        return ResponseEntity.ok().body(this.repository.save(boletoTeste));
    }

    @GetMapping("/list/{id}")
    public Optional<Boleto> listCharacteristicOneBill(@PathVariable Integer id){
        return repository.findById(id);
    }

    @GetMapping("/list/settled/{id}")
    public List<Boleto> listCharacteristicSettledBills(@PathVariable Integer id){
        if (isPresent(id)) return repository.findBoletosByPessoaAndStatus(id, Status.LIQUIDADO);
        return null;
    }
    @GetMapping("/list/canceled/{id}")
    public List<Boleto> listCharacteristicCanceledBills(@PathVariable Integer id){
        if(isPresent(id)) return repository.findBoletosByPessoaAndStatus(id, Status.CANCELADO);
        return null;
    }

    public boolean isPresent(Integer id){
       var test = pessoaRepository.findById(id).orElse(null);
        return test != null;
    }

    @GetMapping("/list/pending/{id}")
    public List<Boleto> listCharacteristicPendingBills(@PathVariable Integer id){
        if (isPresent(id))return repository.findBoletosByPessoaAndStatus(id, Status.PENDENTE);
        return null;
    }

    @PutMapping("/payment/{id}")
    public ResponseEntity payment(@PathVariable Integer id){
        var boleto = this.repository.findById(id).orElse(null);

        if (boleto == null){
            return ResponseEntity
                    .badRequest()
                    .body("Boleto inexistente, verifique e tente novamente!");
        }

        boleto.setStatus(Status.LIQUIDADO);
        this.repository.save(boleto);
        return ResponseEntity.ok().body("Boleto Pago com sucesso!");
    }
    @PutMapping("/cancel/{id}")
    public ResponseEntity cancel(@PathVariable Integer id){
        var boleto = this.repository.findById(id).orElse(null);
        if (boleto == null){
            return ResponseEntity
                    .badRequest()
                    .body("Boleto inexistente, verifique e tente novamente!");
        }
        boleto.setStatus(Status.CANCELADO);
        this.repository.save(boleto);
        return ResponseEntity.ok().body("Cancelamento efetuado com sucesso!");
    }
}