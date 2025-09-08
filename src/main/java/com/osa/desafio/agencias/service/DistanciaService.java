package com.osa.desafio.agencias.service;

import org.springframework.stereotype.Service;

@Service
public class DistanciaService {

    public Double calcularDistancia(Double x1, Double y1, Double x2, Double y2){
        return Math.hypot(x2 - x1, y2 - y1);
    }
}
