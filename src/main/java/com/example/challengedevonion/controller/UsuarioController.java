package com.example.challengedevonion.controller;

import com.example.challengedevonion.model.Usuario;
import com.example.challengedevonion.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("create")
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario){
        return this.service.criar(usuario);
    }
    @PutMapping("")
    public ResponseEntity<Usuario> login(@RequestBody Usuario usuario){
        return this.service.verificaLogin(usuario);
    }
}