package com.example.challengedevonion.repository;

import com.example.challengedevonion.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    Pessoa findPessoaById(Integer id);
}
