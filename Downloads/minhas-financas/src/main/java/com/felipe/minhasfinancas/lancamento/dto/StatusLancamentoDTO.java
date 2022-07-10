package com.felipe.minhasfinancas.lancamento.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class StatusLancamentoDTO {
    private String status;
    private Long idLancamento;
}
