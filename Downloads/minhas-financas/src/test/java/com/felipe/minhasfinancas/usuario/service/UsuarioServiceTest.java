package com.felipe.minhasfinancas.usuario.service;

import com.felipe.minhasfinancas.exceptions.RegraNegocioException;
import com.felipe.minhasfinancas.usuario.model.Usuario;
import com.felipe.minhasfinancas.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test()
    public void deveValidarEmail(){
        createUser();

        Assertions.assertThrows(RegraNegocioException.class, () ->{
            usuarioService.validarEmail("felipe.vianna86@gmail.com");
        });
    }

    private Usuario createUser(){
        Usuario usuario = Usuario.builder()
                .nome("Felipe Vianna")
                .email("felipe.vianna86@gmail.com")
                .build();
        return usuarioRepository.save(usuario);
    }
}
