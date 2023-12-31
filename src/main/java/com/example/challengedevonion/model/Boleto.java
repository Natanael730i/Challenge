package com.example.challengedevonion.model;

import com.example.challengedevonion.model.enums.Status;
import com.example.challengedevonion.model.enums.Vencimentos;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class Boleto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private LocalDate dataVencimento;
    private Vencimentos vencimentos;
    private BigDecimal valor;
    private Integer pessoa;
    private Status status;
    @CreationTimestamp
    private Date createdAt;
}
