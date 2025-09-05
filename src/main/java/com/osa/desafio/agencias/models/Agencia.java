package com.osa.desafio.agencias.models;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name="agencias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Agencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private Double coordenadaX;

    @NotNull
    private Double coordenadaY;
}
