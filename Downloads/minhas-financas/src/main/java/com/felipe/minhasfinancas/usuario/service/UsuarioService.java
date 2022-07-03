package com.felipe.minhasfinancas.usuario.service;

import com.felipe.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.felipe.minhasfinancas.exceptions.RegraNegocioException;
import com.felipe.minhasfinancas.lancamento.service.LancamentoService;
import com.felipe.minhasfinancas.usuario.dto.UsuarioDTO;
import com.felipe.minhasfinancas.usuario.model.Usuario;
import com.felipe.minhasfinancas.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    //private LancamentoService lancamentoService;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        usuario.orElseThrow(() -> new ErroAutenticacaoException("Usuário não encontrado."));

        Usuario usuarioLogado = usuario.get();

        if(!senhaValida(usuarioLogado, senha)){
            throw  new ErroAutenticacaoException("Senha inválida.");
        }

        return usuarioLogado;
    }
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());

        return usuarioRepository.save(usuario);
    }

    public void validarEmail(String email) throws RegraNegocioException {
        boolean existe = usuarioRepository.existsByEmail(email);

        if(existe){
            throw new RegraNegocioException("Já existe um usuário cadastrado com esse e-mail.");
        }
    }
    @Transactional
    public Usuario salvarUsuario(UsuarioDTO usuarioDTO) {

        Usuario usuario = convertDTOToUsuario(usuarioDTO);

        validarEmail(usuario.getEmail());

        return usuarioRepository.save(usuario);
    }
    public Usuario findUsuarioById(Long id) {
        Optional<Usuario> usuario = this.usuarioRepository.findById(id);

        usuario.orElseThrow( () -> new RegraNegocioException("Usuário não encontrado"));

        return usuario.get();
    }

    private boolean senhaValida(Usuario usuario, String senha) {
        return usuario.getSenha().equals(senha);
    }

    private Usuario convertDTOToUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .build();
    }
}
