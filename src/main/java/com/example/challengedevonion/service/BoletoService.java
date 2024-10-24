package com.example.challengedevonion.service;

import com.example.challengedevonion.model.Boleto;
import com.example.challengedevonion.model.Pessoa;
import com.example.challengedevonion.model.enums.Status;
import com.example.challengedevonion.model.enums.Vencimentos;
import com.example.challengedevonion.repository.BoletoRepository;
import com.example.challengedevonion.repository.PessoaRepository;
import com.example.challengedevonion.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class BoletoService {

    @Autowired
    private final BoletoRepository boletoRepository;
    private final PessoaRepository pessoaRepository;

    public BoletoService(BoletoRepository boletoRepository, PessoaRepository pessoaRepository) {
        this.boletoRepository = boletoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    private Pessoa findPessoa(Boleto boleto){
        var pessoa = pessoaRepository
                .findById(boleto.getPessoa().getId())
                .orElse(null);
        if (pessoa == null){
            return new Pessoa();
        }
        return pessoa;
    }

    public List<Boleto> getListaBoletosPendentesPessoa(UUID id){
        return boletoRepository
                .findBoletosByPessoaAndStatus(id,
                        Status.PENDENTE);
    }

    public List<Boleto> getListaBoletosCancelados(UUID id){
        return boletoRepository
                .findBoletosByPessoaAndStatus(id, Status.CANCELADO);
    }

    public List<Boleto> getListBoletosPagos(UUID id){
        return boletoRepository
                .findBoletosByPessoaAndStatus(id, Status.LIQUIDADO);
    }

    private boolean getValorLimite(Boleto boleto){
        var boletos = getListaBoletosPendentesPessoa(boleto.getPessoa().getId());

        double limite = 0;
        for ( Boleto bol : boletos){
            limite += bol.getValor().doubleValue();
        }
        var pessoa = findPessoa(boleto);
        return !(limite + boleto
                .getValor()
                .doubleValue() >= pessoa
                .getValorLimiteBoletos()
                .doubleValue());
    }

    public ResponseEntity<List<Boleto>> listar(){
        List<Boleto> boletoList = boletoRepository.findAll();
        return ResponseEntity.ok().body(boletoList);
    }

    public ResponseEntity<Boleto> criar(Boleto boleto){
        var dataAtual = LocalDate.now();
        var pessoa = findPessoa(boleto);
        var pessoaLimite = getValorLimite(boleto);

        if(boleto.getVencimentos() == null || boleto.getDataVencimento() == null){
            boleto.setVencimentos(Vencimentos.TRINTA_DIAS);
            boleto.setDataVencimento(dataAtual.plusDays(30));
        }
        if(boleto.getDataVencimento().isEqual(dataAtual) || boleto.getDataVencimento().isBefore(dataAtual)){
            return ResponseEntity.badRequest().body(boleto);
        }
        if(pessoa.getId() == null){
            return ResponseEntity.badRequest().body(boleto);
        }
        if (!pessoaLimite){
            return ResponseEntity.badRequest().body(boleto);
        }
        boleto.setStatus(Status.PENDENTE);
        return ResponseEntity.ok().body(boletoRepository.save(boleto));
    }

    private boolean isPresentBoolean(UUID id) throws ChangeSetPersister.NotFoundException {
        var retornarObjeto = boletoRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        return retornarObjeto != null;
    }

    private Boleto isPresentBoleto(UUID id){
        return boletoRepository.findById(id).orElse(null);
    }

    private Boleto retornarObjeto(Boleto boleto, UUID id){
        var retornarObjeto = isPresentBoleto(id);
        if (retornarObjeto == null){
            return new Boleto();
        }
        Utils.copyNonNullProperties(retornarObjeto, boleto);
        return retornarObjeto;
    }

    public ResponseEntity<Boleto> alterar(Boleto boleto, UUID id){
        var newBoleto = retornarObjeto(boleto, id);
        if (newBoleto.getId() == null){
            return ResponseEntity.notFound().build();
        }
        boletoRepository.save(newBoleto);
        return ResponseEntity.ok().body(newBoleto);
    }

    public Boleto encontraBoletoId(UUID id){
        return boletoRepository.findById(id).orElse(null);
    }

    public ResponseEntity<Boleto> cancelar(UUID id) throws ChangeSetPersister.NotFoundException {
        var boleto = encontraBoletoId(id);
        if (!isPresentBoolean(id)){
            return ResponseEntity.notFound().build();
        }
        boletoRepository.save(boleto);
        boleto.setStatus(Status.CANCELADO);
        return ResponseEntity.ok().body(boleto);
    }

    public ResponseEntity<Boleto> pagar(UUID id){
        var boleto = encontraBoletoId(id);
        if (boleto == null){
            return ResponseEntity.notFound().build();
        }
        boleto.setStatus(Status.LIQUIDADO);
        boletoRepository.save(boleto);
        return ResponseEntity.ok().body(boleto);
    }

    public ResponseEntity<Boleto> retornaUmBoleto(UUID id){
        var teste = encontraBoletoId(id);
        if (teste == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(encontraBoletoId(id));
    }

    public ResponseEntity<List<Boleto>> listaBoletosPendentes(UUID id){
        var boletos = getListaBoletosPendentesPessoa(id);
        if (boletos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(boletos);
    }

    public ResponseEntity<List<Boleto>> listaBoletosPagos(UUID id){
        var boletos = getListBoletosPagos(id);
        if (boletos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(boletos);
    }

    public ResponseEntity<List<Boleto>> listaBoletosCancelados(UUID id){
        var boletos = getListaBoletosCancelados(id);
        if (boletos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(boletos);
    }
}