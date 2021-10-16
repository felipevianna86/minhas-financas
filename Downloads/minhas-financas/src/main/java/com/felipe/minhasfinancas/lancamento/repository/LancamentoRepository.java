package com.felipe.minhasfinancas.lancamento.repository;

import com.felipe.minhasfinancas.lancamento.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
