package com.felipe.minhasfinancas.lancamento.dto;

import lombok.Getter;

@Getter
public class FiltroLancamentoDTO {

    private String descricao;
    private Integer mes;
    private Integer ano;
    private Long idUsuario;
}
