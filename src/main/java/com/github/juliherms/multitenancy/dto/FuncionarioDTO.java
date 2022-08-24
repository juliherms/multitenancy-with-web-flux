package com.github.juliherms.multitenancy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FuncionarioDTO {

    private final Long id;
    private final String nome;
    private final String sobrenome;

    @JsonCreator
    public FuncionarioDTO(@JsonProperty("id") Long id,
                          @JsonProperty("nome") String nome,
                          @JsonProperty("sobrenome") String sobrenome) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("nome")
    public String getNome() {
        return nome;
    }

    @JsonProperty("sobrenome")
    public String getSobrenome() {
        return sobrenome;
    }
}
