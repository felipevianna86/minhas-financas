package com.felipe.minhasfinancas.enums;

public enum StatusLancamentoEnum {
    PENDENTE,
    CANCELADO,
    PAGO;

    public static StatusLancamentoEnum getStatusLancamento(String status){
        if(status.equals(PENDENTE)){
            return PENDENTE;
        }else if(status.equals(CANCELADO)){
            return CANCELADO;
        }else if(status.equals(PAGO)){
            return PAGO;
        }
        else{
            return null;
        }
    }
}
