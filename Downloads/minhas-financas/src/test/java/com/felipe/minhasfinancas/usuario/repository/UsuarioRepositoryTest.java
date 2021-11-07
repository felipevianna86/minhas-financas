package com.felipe.minhasfinancas.usuario.repository;

import com.felipe.minhasfinancas.usuario.model.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void deveVerificarExistenciaEmail(){
        //cenário
        Usuario usuario = buildUsuario();
        testEntityManager.persist(usuario);

        //ação
        boolean existe = usuarioRepository.existsByEmail("felipe.vianna86@gmail.com");

        //verificação
        Assertions.assertThat(existe).isTrue();
    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail(){

        boolean existe = usuarioRepository.existsByEmail("felipe.vianna86@gmail.com");

        Assertions.assertThat(existe).isFalse();
    }

    private Usuario buildUsuario(){
        return Usuario.builder()
                .nome("Felipe Vianna")
                .email("felipe.vianna86@gmail.com")
                .build();
    }
}
