package com.felipe.minhasfinancas.usuario.repository;

import com.felipe.minhasfinancas.usuario.model.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    public void deveVerificarExistenciaEmail(){
        //cenário
        Usuario usuario = Usuario.builder()
                .nome("Felipe Vianna")
                .email("felipe.vianna86@gmail.com")
                .build();
        usuarioRepository.save(usuario);
        //ação
        boolean existe = usuarioRepository.existsByEmail("felipe.vianna86@gmail.com");

        //verificação
        Assertions.assertThat(existe).isTrue();
    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail(){
        usuarioRepository.deleteAll();

        boolean existe = usuarioRepository.existsByEmail("felipe.vianna86@gmail.com");

        Assertions.assertThat(existe).isFalse();
    }
}
