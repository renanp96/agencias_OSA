package com.osa.desafio.agencias.service;

import com.osa.desafio.agencias.models.Agencia;
import com.osa.desafio.agencias.repository.AgenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgenciaService {

    @Autowired
    private AgenciaRepository agenciaRepository;

    public List<Agencia> getAgencias(){
        return agenciaRepository.findAll();
    }

    public Optional<Agencia> getAgenciaPeloID(Long id){
        return agenciaRepository.findById(id);
    }

    public Agencia postNovaAgencia(Agencia agencia) {
        if(agenciaRepository.existsByNome(agencia.getNome())){throw new IllegalArgumentException("Agencia já cadastrada no sistema!");
        }

        return agenciaRepository.save(agencia);
    }

    public void deleteAgenciaPeloID(Long id){
        agenciaRepository.deleteById(id);
    }
}
