package com.felipe.minhasfinancas.usuario.service;

import com.felipe.minhasfinancas.exceptions.RegraNegocioException;
import com.felipe.minhasfinancas.usuario.model.Usuario;
import com.felipe.minhasfinancas.usuario.repository.UsuarioRepository;
import com.felipe.minhasfinancas.usuario.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    UsuarioService usuarioService;

    @MockBean
    UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp(){
        this.usuarioService = new UsuarioServiceImpl(this.usuarioRepository);
    }

    @Test()
    public void deveValidarEmailUsuarioNaoExistente(){
        Mockito.when(this.usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

        this.usuarioService.validarEmail("felipe@email.com");
    }

    @Test()
    public void deveValidarEmailUsuarioExistente(){
        Mockito.when(this.usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

        Assertions.assertThrows(RegraNegocioException.class, () ->{
            usuarioService.validarEmail("felipe.vianna86@gmail.com");
        });
    }

}
