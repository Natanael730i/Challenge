package com.example.challengedevonion.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class Boleto {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Nullable
    private Date dataVencimento;
    private BigDecimal valor;
    private UUID pessoa;
    private Status status;
}
