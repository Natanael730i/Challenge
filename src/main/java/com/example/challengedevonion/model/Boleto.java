package com.example.challengedevonion.model;

import com.example.challengedevonion.model.enums.Status;
import com.example.challengedevonion.model.enums.Vencimentos;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate dataVencimento;

    private Vencimentos vencimentos;

    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "pessoa")
    private Pessoa pessoa;

    private Status status;

    @CreationTimestamp
    private Date createdAt;

}
