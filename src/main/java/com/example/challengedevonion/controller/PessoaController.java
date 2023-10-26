package com.example.challengedevonion.controller;

import com.example.challengedevonion.model.Pessoa;
import com.example.challengedevonion.repository.BoletoRepository;
import com.example.challengedevonion.repository.PessoaRepository;
import com.example.challengedevonion.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired PessoaRepository repository;
    @Autowired BoletoRepository boletoRepository;

    //funcionando normalmente
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Pessoa pessoa){
        if (pessoa.getValorLimiteBoletos()== null){
            return ResponseEntity.badRequest().body("Pessoa sem limite, não é possível savar");
        }
        return ResponseEntity.ok(repository.save(pessoa));
    }

    //funcionando normalmente
    @GetMapping("/list")
    public List<Pessoa> list(){
        return repository.findAll();
    }

    //Funcionando normalmente
    @PutMapping("/alter/{id}")
    public ResponseEntity alter(@RequestBody Pessoa pessoa, @PathVariable Integer id){
        var pessoaTeste = this.repository.findById(id).orElse(null);
        if (pessoaTeste == null){
            return ResponseEntity
                    .badRequest()
                    .body("Pessoa inexistente, verifique e tente novamente!");
        }
        Utils.copyNonNullProperties(pessoa, pessoaTeste);
        this.repository.save(pessoaTeste);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pessoa alterada com sucesso!");
    }


    //funcionando normalmente
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
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
