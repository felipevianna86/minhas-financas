package com.felipe.minhasfinancas.util;

import org.apache.logging.log4j.util.Strings;

public class StringUtil {

    public static boolean isEmpty(String s){

        if(Strings.isEmpty(s) || Strings.isBlank(s)){
            return true;
        }

        return false;
    }
}
