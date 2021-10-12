package com.felipe.minhasfinancas.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public enum FuncaoEnum {

    DEBITO(0,"DÉBITO"),
    CREDITO_NUBANK(1,"CRÉDITO NUBANK"),
    CREDITO_INTER(2,"CRÉDITO INTER");

    private Integer id;
    private String funcao;

    public Map<Integer, String> getValues(){
        return Arrays.stream(FuncaoEnum.values())
                .collect(Collectors.toMap(k -> k.id, v -> v.funcao));
    }
}
