package com.example.challengedevonion.controller;

import com.example.challengedevonion.model.Usuario;
import com.example.challengedevonion.model.record.AuthenticationDTO;
import com.example.challengedevonion.model.record.LoginResponseDTO;
import com.example.challengedevonion.model.record.RegisterDTO;
import com.example.challengedevonion.service.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/")
public class AuthenticationController {

    private final AuthorizationService service;

    public AuthenticationController(AuthorizationService service) {
        this.service = service;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> create(@RequestBody @Valid AuthenticationDTO usuario){
        return this.service.criar(usuario);
    }

    @PostMapping("register")
    public ResponseEntity<Usuario> registry(@RequestBody @Valid RegisterDTO data){
        return this.service.registrar(data);
    }
    @PutMapping("")
    public ResponseEntity<Usuario> login(@RequestBody Usuario usuario){
        return this.service.verificaLogin(usuario);
    }
}