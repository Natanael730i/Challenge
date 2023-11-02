package com.example.challengedevonion.controller;

import com.example.challengedevonion.model.Pessoa;
import com.example.challengedevonion.service.PessoaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {


    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    //funcionando normalmente
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
    public ResponseEntity<Pessoa> findOne(@PathVariable Integer id){
        return ResponseEntity.ok().body(service.listarPorId(id));
    }

    //Funcionando normalmente
    @PutMapping("/alter/{id}")
    public ResponseEntity<Pessoa> alter(@RequestBody Pessoa pessoa, @PathVariable Integer id){
         return this.service.alterar(pessoa, id);
    }


    //funcionando normalmente
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Pessoa> delete(@PathVariable Integer id){
        return this.service.deletar(id);
    }
}
