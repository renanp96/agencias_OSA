package com.osa.desafio.agencias.controllers;

import com.osa.desafio.agencias.models.Agencia;
import com.osa.desafio.agencias.service.AgenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/desafio")
@Tag(name = "Agencia", description = "API para gerenciar agências e consultar suas localizações")
public class AgenciaController {

    @Autowired
    private AgenciaService agenciaService;

    @GetMapping
    @Operation(summary = "Lista todas as agências.")
    public List<Agencia> getTodasAgencias(){
        return agenciaService.getAgencias();
    }

    @GetMapping("/distancia")
    @Operation(summary = "Retorna as agências mais próximas com base nas coordenadas informadas.")
    public List<Map<String, Object>> getBuscarAgenciaProxima(@RequestParam double coordX, @RequestParam double coordY) {
        return agenciaService.getAgenciaMaisProxima(coordX, coordY);
    }


    @GetMapping("consultar/{id}")
    @Operation(summary = "Retorna uma agencia com base no seu ID.")
    public Agencia getBuscaAgenciaPorId(@PathVariable Long id){
        return agenciaService.getAgenciaPeloID(id);
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastra uma nova agência")
    public Agencia postCadastrarAgencia(@Valid @RequestBody Agencia agencia) {
        return agenciaService.postNovaAgencia(agencia);
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deleta uma agência com base no seu ID.")
    public void deleteAgencia(@PathVariable Long id){
        agenciaService.deleteAgenciaPeloID(id);
    }
}