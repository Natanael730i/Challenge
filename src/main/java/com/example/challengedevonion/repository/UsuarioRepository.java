package com.example.challengedevonion.repository;

import com.example.challengedevonion.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByUsuario(String usuario);

}
