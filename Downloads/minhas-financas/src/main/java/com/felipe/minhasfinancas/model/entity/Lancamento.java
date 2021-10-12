package com.felipe.minhasfinancas.model.entity;

import com.felipe.minhasfinancas.enums.FuncaoLancamentoEnum;
import com.felipe.minhasfinancas.enums.StatusLancamentoEnum;
import com.felipe.minhasfinancas.enums.TipoLancamentoEnum;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "lancamento", schema = "financas")
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "mes")
    private Integer mes;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "funcao")
    @Enumerated(EnumType.ORDINAL)
    private FuncaoLancamentoEnum funcao;

    @Column(name = "qtd_parcelas")
    private Integer qtdParcelas;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "data_cadastro")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataCadastro;

    @Column(name = "data_compra")
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate dataCompra;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private TipoLancamentoEnum tipo;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusLancamentoEnum status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lancamento that = (Lancamento) o;
        return Objects.equals(id, that.id) && Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao);
    }

    @Override
    public String toString() {
        return "Lancamento{" +
                "descricao='" + descricao + '\'' +
                '}';
    }
}
