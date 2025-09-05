package com.osa.desafio.agencias.controllers;

import com.osa.desafio.agencias.models.Agencia;
import com.osa.desafio.agencias.service.AgenciaService;
import com.osa.desafio.agencias.service.DistanciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/desafio")
public class AgenciaController {

    @Autowired
    private AgenciaService agenciaService;

    @Autowired
    private DistanciaService distanciaService;

    //Get Mappings//

    @GetMapping
    public List<Agencia> getTodasAgencias(){
        return agenciaService.getAgencias();
    }

    @GetMapping("/distancia")
    public List<Map<String, Object>> getBuscarDistancia(@RequestParam double coordX, @RequestParam Double coordY){
        List<Agencia> agencias = agenciaService.getAgencias();
        List<Map<String, Object>> resultado = new ArrayList<>();

        for(Agencia agencia : agencias) {
            double distancia = distanciaService.calcularDistancia(coordX, coordY, agencia.getCoordenadaX(), agencia.getCoordenadaY());

            Map<String, Object> dados = new HashMap<>();
            dados.put("id", agencia.getId());
            dados.put("nome", agencia.getNome());
            dados.put("coordX", agencia.getCoordenadaX());
            dados.put("coordY", agencia.getCoordenadaY());
            dados.put("distancia", distancia);

            resultado.add(dados);
        }

        resultado.sort(Comparator.comparingDouble(m -> (double) m.get("distancia")));
        return resultado;
    }

    //Post Mappings//
    @PostMapping("/cadastrar")
    public Agencia postCadastrarAgencia(@RequestBody Agencia agencia) {
        return agenciaService.postNovaAgencia(agencia);
    }

    //Put Mappings//

    //Delete Mappings//
}
