package com.example.challengedevonion.model.enums;

import lombok.Getter;

@Getter
public enum Status {

    PENDENTE("pendente"),
    LIQUIDADO("liquidado"),
    CANCELADO("cancelado");

    private final String descricao;

    Status(String descricao){
        this.descricao = descricao;
    }

}