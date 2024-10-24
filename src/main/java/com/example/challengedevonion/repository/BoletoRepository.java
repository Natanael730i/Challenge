package com.example.challengedevonion.repository;

import com.example.challengedevonion.model.Boleto;
import com.example.challengedevonion.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface BoletoRepository extends JpaRepository<Boleto, UUID> {

    List<Boleto> findBoletoByPessoa(UUID id);

    List<Boleto> findBoletosByPessoaAndStatus(UUID id, Status status);

}
