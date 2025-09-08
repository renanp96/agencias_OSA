package com.osa.desafio.agencias.controllers;

import com.osa.desafio.agencias.models.Agencia;
import com.osa.desafio.agencias.service.AgenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/desafio")
public class AgenciaController {

    @Autowired
    private AgenciaService agenciaService;

    //Get Mappings//

    @GetMapping
    public List<Agencia> getTodasAgencias(){
        return agenciaService.getAgencias();
    }

    @GetMapping("/distancia")
    public Map<String, Object> getBuscarAgenciaProxima(@RequestParam double coordX, @RequestParam double coordY){
        return agenciaService.getAgenciaMaisProxima(coordX, coordY);
    }

    //Post Mappings//
    @PostMapping("/cadastrar")
    public Agencia postCadastrarAgencia(@RequestBody Agencia agencia) {
        return agenciaService.postNovaAgencia(agencia);
    }

    @DeleteMapping("/{id}")
    public void deleteAgencia(@PathVariable Long id){
        agenciaService.deleteAgenciaPeloID(id);
    }
}