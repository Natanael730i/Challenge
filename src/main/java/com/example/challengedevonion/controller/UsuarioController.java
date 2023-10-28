package com.example.challengedevonion.controller;

import com.example.challengedevonion.model.Usuario;
import com.example.challengedevonion.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class UsuarioController {

    UsuarioRepository repository;

    @PostMapping("create")
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario){
        var user = this.repository.findByUsuario(usuario.getUsuario());
        if(user != null){
            return ResponseEntity.badRequest().body(usuario);
        }
        this.repository.save(usuario);
        return ResponseEntity.ok().body(usuario);
    }

}
