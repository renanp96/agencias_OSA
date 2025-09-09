package com.osa.desafio.agencias.service;

import com.osa.desafio.agencias.handler.AgenciaJaCadastradaException;
import com.osa.desafio.agencias.handler.AgenciaNaoEncontrada;
import com.osa.desafio.agencias.models.Agencia;
import com.osa.desafio.agencias.repository.AgenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AgenciaService {

    @Autowired
    private AgenciaRepository agenciaRepository;

    @Autowired
    private DistanciaService distanciaService;

    public List<Agencia> getAgencias(){
        return agenciaRepository.findAll();
    }

    public Agencia getAgenciaPeloID(Long id){
        return agenciaRepository.findById(id).orElseThrow(() -> new AgenciaNaoEncontrada(("Agencia não encontrada no sistema.")));
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
                .min(Comparator.comparingDouble(m -> (Double) m.get("distancia")))
                .orElseThrow(() -> new AgenciaNaoEncontrada(("Nenhuma agencia encontrada.")));
    }

    public Agencia postNovaAgencia(Agencia agencia) throws AgenciaJaCadastradaException {
        if(agenciaRepository.existsByNome(agencia.getNome())){
            throw new AgenciaJaCadastradaException("Agencia já cadastrada no sistema!");
        }
        return agenciaRepository.save(agencia);
    }

    public void deleteAgenciaPeloID(Long id){
        agenciaRepository.deleteById(id);
    }
}