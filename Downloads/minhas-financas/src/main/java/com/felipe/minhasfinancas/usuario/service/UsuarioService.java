package com.felipe.minhasfinancas.usuario.service;

import com.felipe.minhasfinancas.exceptions.RegraNegocioException;
import com.felipe.minhasfinancas.usuario.dto.UsuarioDTO;
import com.felipe.minhasfinancas.usuario.model.Usuario;

import java.math.BigDecimal;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);

    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email) throws RegraNegocioException;

    Usuario salvarUsuario(UsuarioDTO usuarioDTO);

    Usuario findUsuarioById(Long id);

    BigDecimal consultaSaldoByUsuario(Long idUsuario);
}
