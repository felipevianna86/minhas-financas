package com.felipe.minhasfinancas.lancamento.service;

import com.felipe.minhasfinancas.enums.StatusLancamentoEnum;
import com.felipe.minhasfinancas.enums.TipoLancamentoEnum;
import com.felipe.minhasfinancas.exceptions.RegraNegocioException;
import com.felipe.minhasfinancas.lancamento.model.Lancamento;
import com.felipe.minhasfinancas.lancamento.repository.LancamentoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {

    @SpyBean
    LancamentoService lancamentoService;

    @MockBean
    LancamentoRepository lancamentoRepository;

    @Test
    public void deveSalvarLancamento(){
        Lancamento lancamento = buildLancamento(TipoLancamentoEnum.RECEITA, StatusLancamentoEnum.PENDENTE);
        Mockito.doNothing().when(lancamentoService).validarLancamento(lancamento);

        Lancamento lancamentoSaved = buildLancamento(TipoLancamentoEnum.RECEITA, StatusLancamentoEnum.PENDENTE);
        lancamentoSaved.setId(1L);
        lancamentoSaved.setStatus(StatusLancamentoEnum.PENDENTE);
        Mockito.when(lancamentoRepository.save(lancamento)).thenReturn(lancamentoSaved);

        Lancamento lancamentoSalvo = lancamentoService.salvar(lancamento);

        Assertions.assertThat(lancamentoSalvo.getId()).isEqualTo(lancamentoSaved.getId());
        Assertions.assertThat(lancamentoSalvo.getStatus()).isEqualTo(StatusLancamentoEnum.PENDENTE);

    }

    @Test
    public void naoDeveSalvarLancamentoQuandoValidar(){
        Lancamento lancamentoASalvar = buildLancamento(TipoLancamentoEnum.RECEITA, StatusLancamentoEnum.PENDENTE);
        Mockito.doThrow(RegraNegocioException.class).when(lancamentoService).validarLancamento(lancamentoASalvar);

        Assertions.catchThrowableOfType(() -> lancamentoService.salvar(lancamentoASalvar), RegraNegocioException.class) ;

        Mockito.verify(lancamentoRepository, Mockito.never()).save(lancamentoASalvar);
    }

    @Test
    public void deveAtualizarLancamento(){
        Lancamento lancamentoSaved = buildLancamento(TipoLancamentoEnum.RECEITA, StatusLancamentoEnum.PENDENTE);
        lancamentoSaved.setId(1L);
        Mockito.doNothing().when(lancamentoService).validarLancamento(lancamentoSaved);

        Mockito.when(lancamentoRepository.save(lancamentoSaved)).thenReturn(lancamentoSaved);

        lancamentoService.atualizar(lancamentoSaved);

        Mockito.verify(lancamentoRepository, Mockito.times(1)).save(lancamentoSaved);
    }

    @Test
    public void naoDeveAtualizarLancamentoQuandoValidar(){
        Lancamento lancamentoASalvar = buildLancamento(TipoLancamentoEnum.RECEITA, StatusLancamentoEnum.PENDENTE);

        Assertions.catchThrowableOfType(() -> lancamentoService.atualizar(lancamentoASalvar), NullPointerException.class) ;

        Mockito.verify(lancamentoRepository, Mockito.never()).save(lancamentoASalvar);
    }

    private Lancamento buildLancamento(TipoLancamentoEnum tipo, StatusLancamentoEnum status){
        return Lancamento.builder()
                .ano(2019)
                .mes(1)
                .descricao("Lançamento teste")
                .valor(BigDecimal.TEN)
                .tipo(tipo)
                .status(status)
                .dataCadastro(LocalDate.now())
                .build();
    }
}
