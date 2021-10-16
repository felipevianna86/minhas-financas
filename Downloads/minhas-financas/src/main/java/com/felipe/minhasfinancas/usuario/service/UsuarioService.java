package com.felipe.minhasfinancas.usuario.service;

import com.felipe.minhasfinancas.usuario.model.Usuario;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);

    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email);
}
