package com.example.challengedevonion.service;

import com.example.challengedevonion.model.Pessoa;
import com.example.challengedevonion.repository.BoletoRepository;
import com.example.challengedevonion.repository.PessoaRepository;
import com.example.challengedevonion.utils.Utils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PessoaService {

    private final PessoaRepository repository;
    private final BoletoRepository boletoRepository;

    public PessoaService(PessoaRepository repository, BoletoRepository boletoRepository) {
        this.repository = repository;
        this.boletoRepository = boletoRepository;
    }

    public ResponseEntity<Pessoa> criar(Pessoa pessoa){
        if (pessoa.getValorLimiteBoletos() == null){
            return ResponseEntity.badRequest().body(pessoa);
        }
        repository.save(pessoa);
        return ResponseEntity.ok().body(pessoa);
    }

    public ResponseEntity<List<Pessoa>> listarTodos(){
        var pessoas = this.repository.findAll();
        if (pessoas.isEmpty()){
            return ResponseEntity.ok().body(new ArrayList<>());
        }
        return ResponseEntity.ok().body(pessoas);
    }

    public Pessoa listarPorId(UUID id){
        var pessoa = this.repository.findById(id).orElse(null);
        if (pessoa == null){
            return new Pessoa();
        }
        return pessoa;
    }



    public ResponseEntity<Pessoa> alterar(Pessoa pessoa, UUID id){
        var verificarPessoa = this.verifica(pessoa, id);
        if(ObjectUtils.isEmpty(verificarPessoa)) return ResponseEntity.ok().body(verificarPessoa);
        return ResponseEntity.notFound().build();
    }

    public Pessoa verifica(Pessoa pessoa, UUID id){
        var verificarPessoa = listarPorId(id);
        if (verificarPessoa.getId() == null){
            return null;
        }
        Utils.copyNonNullProperties(pessoa, verificarPessoa);
        return pessoa;
    }

    public ResponseEntity<Pessoa> deletar(UUID id){
        var pessoa = listarPorId(id);
        if (pessoa == null){
            return ResponseEntity.notFound().build();
        }
        var boletos = this.boletoRepository.findBoletoByPessoa(id);
        if (!(boletos.isEmpty())){
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(boletos);
        }
        this.repository.deleteById(id);
        return ResponseEntity.ok().body(pessoa);
    }
}
