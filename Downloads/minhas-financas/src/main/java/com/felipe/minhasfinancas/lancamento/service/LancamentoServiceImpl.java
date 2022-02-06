package com.felipe.minhasfinancas.lancamento.service;

import com.felipe.minhasfinancas.dto.ExceptionDTO;
import com.felipe.minhasfinancas.enums.FuncaoLancamentoEnum;
import com.felipe.minhasfinancas.enums.StatusLancamentoEnum;
import com.felipe.minhasfinancas.enums.TipoLancamentoEnum;
import com.felipe.minhasfinancas.exceptions.RegraNegocioException;
import com.felipe.minhasfinancas.lancamento.dto.LancamentoDTO;
import com.felipe.minhasfinancas.lancamento.model.Lancamento;
import com.felipe.minhasfinancas.lancamento.repository.LancamentoRepository;
import com.felipe.minhasfinancas.usuario.service.UsuarioService;
import com.felipe.minhasfinancas.util.NumberUtil;
import com.felipe.minhasfinancas.util.StringUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private LancamentoRepository lancamentoRepository;

    private UsuarioService usuarioService;

    @Autowired
    public LancamentoServiceImpl(LancamentoRepository lancamentoRepository,
                                 UsuarioService usuarioService){
        this.lancamentoRepository = lancamentoRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    @Transactional
    public Lancamento salvar(Lancamento lancamento) {
        validarLancamento(lancamento);
        lancamento.setStatus(StatusLancamentoEnum.PENDENTE);
        return this.lancamentoRepository.save(lancamento);
    }

    @Override
    public Lancamento atualizar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        validarLancamento(lancamento);
        return this.lancamentoRepository.save(lancamento);
    }

    @Override
    public void deletar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        this.lancamentoRepository.delete(lancamento);
    }

    @Override
    public List<Lancamento> buscar(Lancamento filtro) {

        Example example = Example.of(filtro,
                ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
        );

        return this.lancamentoRepository.findAll(example);
    }

    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamentoEnum status) {
        lancamento.setStatus(status);
        atualizar(lancamento);
    }

    @Override
    public void validarLancamento(Lancamento lancamento) {
        ExceptionDTO exceptionDTO = retornaValidacao(lancamento);

        if(!exceptionDTO.isValid()){
            throw new RegraNegocioException(exceptionDTO.getMessageException());
        }
    }

    @Override
    public Lancamento salvar(LancamentoDTO lancamentoDTO) {

        Lancamento lancamento = convertDTO(lancamentoDTO);
        return this.salvar(lancamento);
    }

    @Override
    public Lancamento atualizar(LancamentoDTO lancamentoDTO) {

        Optional<Lancamento> lancamentoDB = this.lancamentoRepository.findById(lancamentoDTO.getId());

        lancamentoDB.orElseThrow(() -> new RegraNegocioException("Lançamento não encontrado!"));

        Lancamento lancamento = convertDTO(lancamentoDTO);
        return this.atualizar(lancamento);
    }

    private Lancamento convertDTO(LancamentoDTO lancamentoDTO){

        return Lancamento.builder()
                .ano(lancamentoDTO.getAno())
                .descricao(lancamentoDTO.getDescricao())
                .dataCompra(lancamentoDTO.getDataCompra())
                .qtdParcelas(lancamentoDTO.getQtdParcelas())
                .funcao(FuncaoLancamentoEnum.getFuncao(lancamentoDTO.getFuncao()))
                .usuario(this.usuarioService.findUsuarioById(lancamentoDTO.getIdUsuario()))
                .tipo(TipoLancamentoEnum.getTipoLancamento(lancamentoDTO.getTipo()))
                .valor(lancamentoDTO.getValor())
                .id(lancamentoDTO.getId())
                .mes(lancamentoDTO.getMes())
                .build();
    }

    private ExceptionDTO retornaValidacao(Lancamento lancamento){
        boolean valid = true;
        String messageException = Strings.EMPTY;

        if(StringUtil.isEmpty(lancamento.getDescricao())){
            valid = false;
            messageException = "Informe uma descrição válida!";
        }else if(!NumberUtil.mesValido(lancamento.getMes())){
            valid = false;
            messageException = "Informe um mês válido!";
        }else if(!NumberUtil.anoValido(lancamento.getAno())){
            valid = false;
            messageException = "Informe um ano válido!";
        }else if(!NumberUtil.valorPositivo(lancamento.getValor())){
            valid = false;
            messageException = "Informe um valor válido!";
        }else if(lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null){
            valid = false;
            messageException = "Informe um usuário!";
        }else if(lancamento.getTipo() == null){
            valid = false;
            messageException = "Informe um tipo de pagamento!";
        }else if(lancamento.getFuncao() == null){
            valid = false;
            messageException = "Informe uma função de pagamento!";
        }

        return ExceptionDTO.builder()
                .valid(valid)
                .messageException(messageException)
                .build();
    }
}
