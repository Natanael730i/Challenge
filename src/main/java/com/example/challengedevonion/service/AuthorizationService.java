package com.example.challengedevonion.service;

import com.example.challengedevonion.config.security.TokenService;
import com.example.challengedevonion.model.Usuario;
import com.example.challengedevonion.model.record.AuthenticationDTO;
import com.example.challengedevonion.model.record.LoginResponseDTO;
import com.example.challengedevonion.model.record.RegisterDTO;
import com.example.challengedevonion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public ResponseEntity<LoginResponseDTO> criar(AuthenticationDTO usuario) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(usuario.login(), usuario.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    public ResponseEntity<Usuario> verificaLogin(Usuario usuario){
        return ResponseEntity.ok().body(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

    public ResponseEntity<Usuario> registrar(RegisterDTO data){
        if (this.repository.findByLogin(data.usuario())!= null) return ResponseEntity.badRequest().build();
        String encryptedPasswor = new BCryptPasswordEncoder().encode(data.password());
        Usuario user = new Usuario(data.usuario(), encryptedPasswor, data.role());
        this.repository.save(user);
        return ResponseEntity.ok().body(user);
    }
}
