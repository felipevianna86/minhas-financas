package com.felipe.minhasfinancas.lancamento.service;

import com.felipe.minhasfinancas.enums.StatusLancamentoEnum;
import com.felipe.minhasfinancas.lancamento.dto.FiltroLancamentoDTO;
import com.felipe.minhasfinancas.lancamento.dto.LancamentoDTO;
import com.felipe.minhasfinancas.lancamento.dto.StatusLancamentoDTO;
import com.felipe.minhasfinancas.lancamento.model.Lancamento;

import java.math.BigDecimal;
import java.util.List;

public interface LancamentoService {

    Lancamento salvar(Lancamento lancamento);

    Lancamento atualizar(Lancamento lancamento);

    void deletar(Lancamento lancamento);

    List<Lancamento> buscar(Lancamento filtro);

    Lancamento atualizarStatus(StatusLancamentoDTO statusLancamentoDTO);

    void validarLancamento(Lancamento lancamento);

    Lancamento salvar(LancamentoDTO lancamentoDTO);

    Lancamento atualizar(LancamentoDTO lancamentoDTO);

    void remover(Long id);

    List<Lancamento> buscar(FiltroLancamentoDTO filtro);

    BigDecimal consultaSaldoByUsuario(Long idUsuario);
}
