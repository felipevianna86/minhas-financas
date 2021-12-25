package com.felipe.minhasfinancas.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public enum FuncaoLancamentoEnum {

    DEBITO(0,"DÉBITO"),
    CREDITO(1,"CRÉDITO");

    private Integer id;
    private String funcao;

    public Map<Integer, String> getValues(){
        return Arrays.stream(FuncaoLancamentoEnum.values())
                .collect(Collectors.toMap(k -> k.id, v -> v.funcao));
    }
}
