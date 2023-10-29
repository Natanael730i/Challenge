package com.example.challengedevonion.repository;

import com.example.challengedevonion.model.Boleto;
import com.example.challengedevonion.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoletoRepository extends JpaRepository<Boleto, Integer> {

    List<Boleto> findBoletoByPessoa(Integer id);

    List<Boleto> findBoletosByPessoaAndStatus(Integer id, Status status);
}
