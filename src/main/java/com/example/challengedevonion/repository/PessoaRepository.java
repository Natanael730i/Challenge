package com.example.challengedevonion.repository;

import com.example.challengedevonion.model.Pessoa;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    public Pessoa findPessoaById(Integer id);
}
