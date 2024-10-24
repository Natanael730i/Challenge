package com.example.challengedevonion.controller;

import com.example.challengedevonion.model.Boleto;
import com.example.challengedevonion.service.BoletoService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/boleto")
public class BoletoController {

    private final BoletoService service;

    public BoletoController(BoletoService service) {
        this.service = service;
    }

    @GetMapping("/list/pessoa/{id}")
    public ResponseEntity<List<Boleto>> listBoletoPessoa(@PathVariable UUID id){
        return ResponseEntity.ok().body(service.getListaBoletosPendentesPessoa(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Boleto>> list(){
        return service.listar();
    }

    @PostMapping("/create")
    public ResponseEntity<Boleto> criar(@RequestBody Boleto boleto){
        return service.criar(boleto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Boleto> alter(@RequestBody Boleto boleto, @PathVariable UUID id){
        return service.alterar(boleto, id);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Boleto> listCharacteristicOneBill(@PathVariable UUID id){
        return service.retornaUmBoleto(id);
    }

    @GetMapping("/list/settled/{id}")
    public ResponseEntity<List<Boleto>> listCharacteristicSettledBills(@PathVariable UUID id){
        return service.listaBoletosPagos(id);
    }

    @GetMapping("/list/canceled/{id}")
    public ResponseEntity<List<Boleto>> listCharacteristicCanceledBills(@PathVariable UUID id){
       return service.listaBoletosCancelados(id);
    }

    @GetMapping("/list/pending/{id}")
    public ResponseEntity<List<Boleto>> listCharacteristicPendingBills(@PathVariable UUID id){
        return service.listaBoletosPendentes(id);
    }

    @PutMapping("/payment/{id}")
    public ResponseEntity<Boleto> payment(@PathVariable UUID id){
        return service.pagar(id);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Boleto> cancel(@PathVariable UUID id) throws ChangeSetPersister.NotFoundException {
        return service.cancelar(id);
    }
}