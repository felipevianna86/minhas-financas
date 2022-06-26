package com.felipe.minhasfinancas.lancamento.repository;

import com.felipe.minhasfinancas.enums.StatusLancamentoEnum;
import com.felipe.minhasfinancas.enums.TipoLancamentoEnum;
import com.felipe.minhasfinancas.lancamento.model.Lancamento;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

    @Autowired
    LancamentoRepository lancamentoRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void deveSalvarLancamento(){
        Lancamento lancamento = buildLancamento(TipoLancamentoEnum.RECEITA, StatusLancamentoEnum.PENDENTE);

        lancamento = lancamentoRepository.save(lancamento);

        Assertions.assertThat(lancamento.getId()).isNotNull();
    }

    @Test
    public void deveDeletarLancamento(){
        Lancamento lancamento = createAndPersistLancamento();

        lancamento = testEntityManager.find(Lancamento.class, lancamento.getId());

        lancamentoRepository.delete(lancamento);
        Lancamento lancamentoRemovido = testEntityManager.find(Lancamento.class, lancamento.getId());
        Assertions.assertThat(lancamentoRemovido).isNull();

    }

    @Test
    public void deveAtualizarLancamento(){
        Lancamento lancamento = createAndPersistLancamento();

        lancamento.setAno(2018);
        lancamento.setDescricao("Teste de atualização");
        lancamento.setStatus(StatusLancamentoEnum.CANCELADO);

        lancamentoRepository.save(lancamento);

        Lancamento lancamentoUpdated = testEntityManager.find(Lancamento.class, lancamento.getId());

        Assertions.assertThat(lancamento.getAno()).isEqualTo(2018);
        Assertions.assertThat(lancamento.getDescricao()).isEqualTo("Teste de atualização");
        Assertions.assertThat(lancamento.getStatus()).isEqualTo(StatusLancamentoEnum.CANCELADO);

    }

    @Test
    public void deveBuscarLancamentoById(){
        Lancamento lancamento = createAndPersistLancamento();

        Optional<Lancamento> lancamentoCreated = lancamentoRepository.findById(lancamento.getId());

        Assertions.assertThat(lancamentoCreated.isPresent()).isTrue();
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

    private Lancamento createAndPersistLancamento(){
        Lancamento lancamento = buildLancamento(TipoLancamentoEnum.RECEITA, StatusLancamentoEnum.PENDENTE);
        testEntityManager.persist(lancamento);
        return lancamento;
    }

}
