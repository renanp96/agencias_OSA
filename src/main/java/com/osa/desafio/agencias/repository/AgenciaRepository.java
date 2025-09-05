package com.osa.desafio.agencias.repository;

import com.osa.desafio.agencias.models.Agencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgenciaRepository extends JpaRepository<Agencia, Long> {
    boolean existsByNome(String nome);
}
