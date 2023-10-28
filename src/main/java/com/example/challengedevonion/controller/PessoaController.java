package com.example.challengedevonion.controller;

import com.example.challengedevonion.model.Pessoa;
import com.example.challengedevonion.repository.BoletoRepository;
import com.example.challengedevonion.repository.PessoaRepository;
import com.example.challengedevonion.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {


    private final PessoaRepository repository;

    private final BoletoRepository boletoRepository;

    public PessoaController(PessoaRepository repository, BoletoRepository boletoRepository) {
        this.repository = repository;
        this.boletoRepository = boletoRepository;
    }

    //funcionando normalmente
    @PostMapping("/create")
    public ResponseEntity<Pessoa> create(@RequestBody Pessoa pessoa){
        if (pessoa.getValorLimiteBoletos()== null){
            return ResponseEntity.badRequest().body(pessoa);
        }
        return ResponseEntity.ok(repository.save(pessoa));
    }

    //funcionando normalmente
    @GetMapping("/list")
    public ResponseEntity<List<Pessoa>> list(){
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }
    @GetMapping("/{id}")
    public Pessoa findOne(@PathVariable Integer id){
        return repository.findPessoaById(id);
    }

    //Funcionando normalmente
    @PutMapping("/alter/{id}")
    public ResponseEntity<Pessoa> alter(@RequestBody Pessoa pessoa, @PathVariable Integer id){
        var pessoaTeste = this.repository.findById(id).orElse(null);
        if (pessoaTeste == null){
            return ResponseEntity
                    .badRequest()
                    .body(null);
        }
        Utils.copyNonNullProperties(pessoa, pessoaTeste);
        this.repository.save(pessoaTeste);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaTeste);
    }


    //funcionando normalmente
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id){
        var pessoa = this.repository.findById(id).orElse(null);
        if (pessoa == null){
            return ResponseEntity
                    .badRequest()
                    .body("Pessoa inexistente, verifique e tente novamente!");
        }
        var boletos = boletoRepository.findBoletoByPessoa(id);
        if(!boletos.isEmpty()){
            return ResponseEntity.badRequest().body("Pessoa tem boletos vinculados, impossível deletar");
        }
        this.repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso!");
    }
}
