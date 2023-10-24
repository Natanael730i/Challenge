package com.example.challengedevonion.repository;

import com.example.challengedevonion.model.Boleto;
import com.example.challengedevonion.model.Pessoa;
import com.example.challengedevonion.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface BoletoRepository extends JpaRepository<Boleto, UUID> {

    public List<Boleto> findBoletoByPessoa(UUID id);

    public List<Boleto> findBoletosByPessoaAndStatus(UUID id, Status status);
}
