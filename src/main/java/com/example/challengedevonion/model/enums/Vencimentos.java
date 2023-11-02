package com.example.challengedevonion.model.enums;

import lombok.Getter;

@Getter
public enum Vencimentos {
    TRINTA_DIAS("30"),
    SESSENTA_DIAS("60"),
    NOVENTA_DIAS("90"),
    CENTO_E_VINTE_DIAS("120");

    private final String descricao;

    Vencimentos( String descricao) {
        this.descricao = descricao;
    }
}
