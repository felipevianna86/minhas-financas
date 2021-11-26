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

import java.util.Optional;

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

    @Test
    public void devePersistirUmUsuarioNaBase(){

        Usuario usuario = buildUsuarioToSave();

        usuario = usuarioRepository.save(usuario);

        Assertions.assertThat(usuario.getId()).isNotNull();
    }

    @Test
    public void deveBuscarUmUsuarioPorEmail(){
        Usuario usuario = buildUsuarioToSave();
        testEntityManager.persist(usuario);

        Optional<Usuario> result = usuarioRepository.findByEmail(usuario.getEmail());

        Assertions.assertThat(result.isPresent()).isTrue();
    }

    @Test
    public void deveRetornarVazioQuandoUmUsuarioNaoExisteNaBase(){
        Optional<Usuario> result = usuarioRepository.findByEmail("felipe.vianna86@gmail.com");

        Assertions.assertThat(result.isPresent()).isFalse();
    }

    private Usuario buildUsuario(){
        return Usuario.builder()
                .nome("Felipe Vianna")
                .email("felipe.vianna86@gmail.com")
                .build();
    }

    private Usuario buildUsuarioToSave(){
        return Usuario.builder()
                .nome("Felipe Vianna")
                .email("felipe.vianna86@gmail.com")
                .senha("senha")
                .build();
    }
}
