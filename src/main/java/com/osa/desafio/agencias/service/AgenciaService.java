package com.osa.desafio.agencias.service;

import com.osa.desafio.agencias.models.Agencia;
import com.osa.desafio.agencias.repository.AgenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AgenciaService {

    @Autowired
    private AgenciaRepository agenciaRepository;

    @Autowired
    private DistanciaService distanciaService;

    public List<Agencia> getAgencias(){
        return agenciaRepository.findAll();
    }

    public Optional<Agencia> getAgenciaPeloID(Long id){
        return agenciaRepository.findById(id);
    }

    public Map<String, Object> getAgenciaMaisProxima(Double coordX, Double coordY) {
        List<Agencia> agencias = getAgencias();

        return agencias.stream()
                .map(agencia -> {
                    double distancia = distanciaService.calcularDistancia(
                            coordX, coordY,
                            agencia.getCoordenadaX(),
                            agencia.getCoordenadaY()
                    );

                    Map<String, Object> dados = new HashMap<>();
                    dados.put("id", agencia.getId());
                    dados.put("nome", agencia.getNome());
                    dados.put("coordX", agencia.getCoordenadaX());
                    dados.put("coordY", agencia.getCoordenadaY());
                    dados.put("distancia", distancia);
                    return dados;
                })
                .min(Comparator.comparingDouble(m -> (Double) m.get("distancia"))) // pega o menor
                .orElse(null);
    }

    public Agencia postNovaAgencia(Agencia agencia) {
        if(agenciaRepository.existsByNome(agencia.getNome())){
            throw new IllegalArgumentException("Agencia já cadastrada no sistema!");
        }
        return agenciaRepository.save(agencia);
    }

    public void deleteAgenciaPeloID(Long id){
        agenciaRepository.deleteById(id);
    }
}