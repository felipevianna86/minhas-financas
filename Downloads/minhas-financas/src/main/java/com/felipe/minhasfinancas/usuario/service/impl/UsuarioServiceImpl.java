package com.felipe.minhasfinancas.usuario.service.impl;

import com.felipe.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.felipe.minhasfinancas.exceptions.RegraNegocioException;
import com.felipe.minhasfinancas.usuario.dto.UsuarioDTO;
import com.felipe.minhasfinancas.usuario.model.Usuario;
import com.felipe.minhasfinancas.usuario.repository.UsuarioRepository;
import com.felipe.minhasfinancas.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        usuario.orElseThrow(() -> new ErroAutenticacaoException("Usuário não encontrado."));

        Usuario usuarioLogado = usuario.get();

        if(!senhaValida(usuarioLogado, senha)){
            throw  new ErroAutenticacaoException("Senha inválida.");
        }

        return usuarioLogado;
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());

        return usuarioRepository.save(usuario);
    }

    @Override
    public void validarEmail(String email) throws RegraNegocioException {
        boolean existe = usuarioRepository.existsByEmail(email);

        if(existe){
            throw new RegraNegocioException("Já existe um usuário cadastrado com esse e-mail.");
        }
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(UsuarioDTO usuarioDTO) {

        Usuario usuario = convertDTOToUsuario(usuarioDTO);

        validarEmail(usuario.getEmail());

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario findUsuarioById(Long id) {
        return this.usuarioRepository.getById(id);
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
