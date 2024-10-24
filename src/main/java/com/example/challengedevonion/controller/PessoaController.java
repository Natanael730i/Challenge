package com.example.challengedevonion.controller;

import com.example.challengedevonion.model.Pessoa;
import com.example.challengedevonion.service.PessoaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Pessoa> create(@RequestBody Pessoa pessoa){
        return service.criar(pessoa);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public ResponseEntity<List<Pessoa>> list(){
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> findOne(@PathVariable UUID id){
        return ResponseEntity.ok().body(service.listarPorId(id));
    }

    @PutMapping("/alter/{id}")
    public ResponseEntity<Pessoa> alter(@RequestBody Pessoa pessoa, @PathVariable UUID id){
         return this.service.alterar(pessoa, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Pessoa> delete(@PathVariable UUID id){
        return this.service.deletar(id);
    }

}
