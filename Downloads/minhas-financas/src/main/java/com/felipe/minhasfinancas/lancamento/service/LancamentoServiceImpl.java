package com.felipe.minhasfinancas.lancamento.service;

import com.felipe.minhasfinancas.enums.StatusLancamentoEnum;
import com.felipe.minhasfinancas.lancamento.model.Lancamento;
import com.felipe.minhasfinancas.lancamento.repository.LancamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private LancamentoRepository lancamentoRepository;

    public LancamentoServiceImpl(LancamentoRepository lancamentoRepository){
        this.lancamentoRepository = lancamentoRepository;
    }

    @Override
    public Lancamento salvar(Lancamento lancamento) {
        return null;
    }

    @Override
    public Lancamento atualizar(Lancamento lancamento) {
        return null;
    }

    @Override
    public void deletar(Lancamento lancamento) {

    }

    @Override
    public List<Lancamento> buscar(Lancamento filtro) {
        return null;
    }

    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamentoEnum status) {

    }
}
