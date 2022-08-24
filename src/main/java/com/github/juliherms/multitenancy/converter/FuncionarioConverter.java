package com.github.juliherms.multitenancy.converter;

import com.github.juliherms.multitenancy.dto.FuncionarioDTO;
import com.github.juliherms.multitenancy.model.Funcionario;

public class FuncionarioConverter {

    private FuncionarioConverter(){}

    public static FuncionarioDTO convert(Funcionario source) {
        if(source == null) {
            return null;
        }
        return new FuncionarioDTO(source.getId(), source.getNome(), source.getSobrenome());
    }

    public static Funcionario convert(FuncionarioDTO source) {
        if(source == null) {
            return null;
        }
        return new Funcionario(source.getId(), source.getNome(), source.getSobrenome());
    }
}
