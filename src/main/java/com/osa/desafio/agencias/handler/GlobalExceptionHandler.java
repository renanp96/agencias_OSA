package com.osa.desafio.agencias.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AgenciaJaCadastradaException.class)
    public ResponseEntity<Map<String, String>> handleAngenciaJaEncontradaException(AgenciaJaCadastradaException ex){
        Map<String, String> erro = new HashMap<>();
        erro.put("erro", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(AgenciaNaoEncontrada.class)
    public ResponseEntity<Map<String, String>> handleAgenciaNaoEncontrada(AgenciaNaoEncontrada ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", ex.getMessage()));
    }
}
