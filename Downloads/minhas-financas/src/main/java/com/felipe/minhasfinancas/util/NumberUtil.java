package com.felipe.minhasfinancas.util;

import java.math.BigDecimal;

public class NumberUtil {

    public static boolean mesValido(Integer mes){
        if(mes != null && (mes >= 1 || mes <= 12 )){
            return true;
        }

        return false;
    }

    public static boolean anoValido(Integer ano){
        if(ano != null && ano.toString().length() == 4){
            return true;
        }

        return false;
    }

    public static boolean valorPositivo(BigDecimal valor){
        if(valor != null && valor.compareTo(BigDecimal.ZERO) > 1){
            return true;
        }

        return false;
    }
}
