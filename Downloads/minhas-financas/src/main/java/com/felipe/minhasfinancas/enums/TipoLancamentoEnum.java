package com.felipe.minhasfinancas.enums;

public enum TipoLancamentoEnum {

    RECEITA,
    DESPESA;

    public static TipoLancamentoEnum getTipoLancamento(String tipo){
        if(tipo.equals(RECEITA)){
            return RECEITA;
        }else if(tipo.equals(DESPESA)){
            return DESPESA;
        }
        else{
            return null;
        }
    }
}
