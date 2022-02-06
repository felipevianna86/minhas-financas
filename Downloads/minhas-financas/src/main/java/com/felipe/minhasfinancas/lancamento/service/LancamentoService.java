package com.felipe.minhasfinancas.lancamento.service;

import com.felipe.minhasfinancas.enums.StatusLancamentoEnum;
import com.felipe.minhasfinancas.lancamento.dto.LancamentoDTO;
import com.felipe.minhasfinancas.lancamento.model.Lancamento;

import java.util.List;

public interface LancamentoService {

    Lancamento salvar(Lancamento lancamento);

    Lancamento atualizar(Lancamento lancamento);

    void deletar(Lancamento lancamento);

    List<Lancamento> buscar(Lancamento filtro);

    void atualizarStatus(Lancamento lancamento, StatusLancamentoEnum status);

    void validarLancamento(Lancamento lancamento);

    Lancamento salvar(LancamentoDTO lancamentoDTO);

    Lancamento atualizar(LancamentoDTO lancamentoDTO);
}
