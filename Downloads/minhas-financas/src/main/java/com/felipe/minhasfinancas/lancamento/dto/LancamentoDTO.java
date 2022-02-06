package com.felipe.minhasfinancas.lancamento.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class LancamentoDTO {

    private Long id;
    private String descricao;
    private Integer mes;
    private Integer ano;
    private Integer funcao;
    private Integer qtdParcelas;
    private Long idUsuario;
    private BigDecimal valor;
    private LocalDate dataCompra;
    private String tipo;
    private String status;
}
