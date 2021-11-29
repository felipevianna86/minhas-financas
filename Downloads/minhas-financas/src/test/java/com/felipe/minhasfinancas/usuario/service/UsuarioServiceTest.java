package com.felipe.minhasfinancas.usuario.service;

import com.felipe.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.felipe.minhasfinancas.exceptions.RegraNegocioException;
import com.felipe.minhasfinancas.usuario.model.Usuario;
import com.felipe.minhasfinancas.usuario.repository.UsuarioRepository;
import com.felipe.minhasfinancas.usuario.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

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

    @Test
    public void deveAutenticarUsuario(){
        String email = "felipe.vianna86@gmail.com";
        String senha = "senha";

        Usuario usuario = getUser(email, senha);

        Mockito.<Optional<Usuario>>when(this.usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        Usuario usuarioLogado = this.usuarioService.autenticar(email, senha);

        org.assertj.core.api.Assertions.assertThat(usuarioLogado).isNotNull();
    }

    @Test
    public void deveRetornarExcecaoPraUsuarioNaoCadastrado(){

        Mockito.when(this.usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(ErroAutenticacaoException.class, () ->{
            this.usuarioService.autenticar("felipe.vianna86@gmail.com", "senha");
        });

        Throwable throwable = org.assertj.core.api.Assertions.catchThrowable( () -> this.usuarioService.autenticar("felipe.vianna86@gmail.com", "senha") );

        org.assertj.core.api.Assertions.assertThat(throwable).isInstanceOf(ErroAutenticacaoException.class).hasMessage("Usuário não encontrado.");

    }

    @Test
    public void deveRetornarExcecaoPraSenhaErrada(){

        Usuario usuario = getUser("felipe.vianna86@gmail.com", "123");

        Mockito.when(this.usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        Assertions.assertThrows(ErroAutenticacaoException.class, () ->{

           this.usuarioService.autenticar("felipe.vianna86@gmail.com", "senha");

        });

        Throwable throwable = org.assertj.core.api.Assertions.catchThrowable( () -> this.usuarioService.autenticar("felipe.vianna86@gmail.com", "senha") );

        org.assertj.core.api.Assertions.assertThat(throwable).isInstanceOf(ErroAutenticacaoException.class).hasMessage("Senha inválida.");

    }

    private Usuario getUser(String email, String senha){
        return Usuario.builder()
                .email(email)
                .senha(senha)
                .id(1L)
                .build();
    }
}
