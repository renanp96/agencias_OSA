package com.osa.desafio.agencias.controllers;

import com.osa.desafio.agencias.models.Agencia;
import com.osa.desafio.agencias.service.AgenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/desafio")
@Tag(name = "Agencia", description = "API para gerenciar agências e consultar suas localizações")
public class AgenciaController {

    @Autowired
    private AgenciaService agenciaService;

    //Get Mappings//

    @GetMapping
    @Operation(summary = "Lista todas as agências.")
    public List<Agencia> getTodasAgencias(){
        return agenciaService.getAgencias();
    }

    @GetMapping("/distancia")
    @Operation(summary = "Retorna as agencias mais proximas com base nas coordenadas informadas.")
    public Map<String, Object> getBuscarAgenciaProxima(@RequestParam double coordX, @RequestParam double coordY){
        return agenciaService.getAgenciaMaisProxima(coordX, coordY);
    }

    //Post Mappings//
    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastra uma nova agência")
    public Agencia postCadastrarAgencia(@RequestBody Agencia agencia) {
        return agenciaService.postNovaAgencia(agencia);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma agência com base no seu ID.")
    public void deleteAgencia(@PathVariable Long id){
        agenciaService.deleteAgenciaPeloID(id);
    }
}