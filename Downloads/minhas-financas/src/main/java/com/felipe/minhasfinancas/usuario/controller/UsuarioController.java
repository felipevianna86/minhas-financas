package com.felipe.minhasfinancas.usuario.controller;

import com.felipe.minhasfinancas.exceptions.ErroAutenticacaoException;
import com.felipe.minhasfinancas.exceptions.RegraNegocioException;
import com.felipe.minhasfinancas.usuario.dto.UsuarioDTO;
import com.felipe.minhasfinancas.usuario.model.Usuario;
import com.felipe.minhasfinancas.usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity salvarUsuario(@RequestBody UsuarioDTO usuarioDTO){

        try {
            Usuario usuario = this.usuarioService.salvarUsuario(usuarioDTO);
            return new ResponseEntity(usuario, HttpStatus.CREATED);
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDTO usuarioDTO){
        try{
            Usuario usuarioAutenticado = usuarioService.autenticar(usuarioDTO.getEmail(), usuarioDTO.getSenha());
            return ResponseEntity.ok(usuarioAutenticado);
        }catch (ErroAutenticacaoException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
