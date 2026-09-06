package com.r_cechim.Controle.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "linha")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Linha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 150)
    private String origem;

    @Column(length = 150)
    private String destino;

    @Column(name = "distancia_km", precision = 10, scale = 2)
    private java.math.BigDecimal distanciaKm;

    @Column(columnDefinition = "TEXT")
    private String observacoes;
}
