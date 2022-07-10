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
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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

    @Test
    public void deveDeletarLancamento(){
        Lancamento lancamentoSaved = buildLancamento(TipoLancamentoEnum.RECEITA, StatusLancamentoEnum.PENDENTE);
        lancamentoSaved.setId(1L);

        lancamentoService.deletar(lancamentoSaved);

        Mockito.verify(lancamentoRepository).delete(lancamentoSaved);
    }

    @Test
    public void naoDeveDeletarLancamentoQuandoValidar(){
        Lancamento lancamentoSaved = buildLancamento(TipoLancamentoEnum.RECEITA, StatusLancamentoEnum.PENDENTE);

        Assertions.catchThrowableOfType(() -> lancamentoService.deletar(lancamentoSaved), NullPointerException.class) ;

        Mockito.verify(lancamentoRepository, Mockito.never()).delete(lancamentoSaved);
    }

    @Test
    public void deveBuscarLancamento(){
        Lancamento lancamento = buildLancamento(TipoLancamentoEnum.RECEITA, StatusLancamentoEnum.PENDENTE);
        lancamento.setId(1L);

        List<Lancamento> lancamentos = Arrays.asList(lancamento);

        Mockito.when(lancamentoRepository.findAll(Mockito.any(Example.class))).thenReturn(lancamentos);

        List<Lancamento> listResult = lancamentoService.buscar(lancamento);

        Assertions.assertThat(listResult).isNotEmpty().hasSize(1).contains(lancamento);
    }

    /*@Test
    public void deveAtualizarStatusLancamento(){
        Lancamento lancamento = buildLancamento(TipoLancamentoEnum.RECEITA, StatusLancamentoEnum.PENDENTE);
        lancamento.setId(1L);
        StatusLancamentoEnum novoStatus = StatusLancamentoEnum.PAGO;

        Mockito.doNothing().when(lancamentoService).validarLancamento(lancamento);
       // Mockito.when(lancamentoRepository.save(lancamento)).thenReturn(lancamento);

        //Lancamento lancamentoDB = lancamentoService.salvar(lancamento);

        Optional<Lancamento> optionalLancamento = Optional.of(lancamento);
        Mockito.doNothing().when(lancamentoService).validaLancamentoBuscado(optionalLancamento);
        Mockito.when(lancamentoRepository.findById(lancamento.getId())).thenReturn(optionalLancamento);

        StatusLancamentoDTO statusLancamentoDTO = buildStatusLancamentoDTO(novoStatus.toString(), lancamento.getId());

        Mockito.when(lancamentoService.atualizarStatus(statusLancamentoDTO)).thenReturn(lancamento);

        lancamento = lancamentoService.atualizarStatus(statusLancamentoDTO);

        Assertions.assertThat(lancamento.getStatus()).isEqualTo(novoStatus);
        Mockito.verify(lancamentoService).atualizar(lancamento);
    }*/

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

    /*private StatusLancamentoDTO buildStatusLancamentoDTO(String status, Long idLancamento){
        return StatusLancamentoDTO.builder()
                .idLancamento(idLancamento)
                .status(status)
                .build();
    }*/
}
