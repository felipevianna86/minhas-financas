package com.felipe.minhasfinancas.lancamento.controller;

import com.felipe.minhasfinancas.lancamento.dto.LancamentoDTO;
import com.felipe.minhasfinancas.lancamento.model.Lancamento;
import com.felipe.minhasfinancas.lancamento.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoController {

    @Autowired
    private LancamentoService lancamentoService;

    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentoDTO lancamentoDTO){
        try {
            Lancamento lancamento = this.lancamentoService.salvar(lancamentoDTO);
            return new ResponseEntity(lancamento, HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO lancamentoDTO){
        try {
            lancamentoDTO.setId(id);
            Lancamento lancamento = this.lancamentoService.atualizar(lancamentoDTO);
            return ResponseEntity.ok(lancamento);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
