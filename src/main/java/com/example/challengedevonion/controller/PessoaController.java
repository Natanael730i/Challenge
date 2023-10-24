package com.example.challengedevonion.controller;

import com.example.challengedevonion.model.Pessoa;
import com.example.challengedevonion.repository.PessoaRepository;
import com.example.challengedevonion.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired PessoaRepository repository;

    //funcionando normalmente
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Pessoa pessoa){
        repository.save(pessoa);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Pessoa criada com secesso");
    }

    //funcionando normalmente
    @GetMapping("/list")
    public List<Pessoa> list(){
        return repository.findAll();
    }

    //Funcionando normalmente
    @PutMapping("/alter/{id}")
    public ResponseEntity alter(@RequestBody Pessoa pessoa, @PathVariable UUID id){
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
    public ResponseEntity delete(@PathVariable String id){
        var pessoa = this.repository.findById(UUID.fromString(id)).orElse(null);
        if (pessoa == null){
            return ResponseEntity
                    .badRequest()
                    .body("Pessoa inexistente, verifique e tente novamente!");
        }
        this.repository.deleteById(UUID.fromString(id));
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso!");
    }
}
