package com.example.challengedevonion.service;

import com.example.challengedevonion.model.Usuario;
import com.example.challengedevonion.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Usuario> criar(Usuario usuario){
        var user = this.repository.findByUsuario(usuario.getUsuario());

        if(user != null){
            return ResponseEntity.badRequest().body(usuario);
        }

//        var newPassword = BCrypt
//                .withDefaults()
//                .hashToString(12, usuario.getSenha()
//                        .toCharArray());
//        usuario.setSenha(newPassword);

        repository.save(usuario);
        return ResponseEntity.ok().body(usuario);
    }

    public ResponseEntity<Usuario> verificaLogin(Usuario usuario){
        return ResponseEntity.ok().body(usuario);
    }
}
