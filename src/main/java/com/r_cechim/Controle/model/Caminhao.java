package com.r_cechim.Controle.model;

import com.r_cechim.Controle.model.enums.StatusCaminhao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "caminhao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Caminhao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String placa;

    @Column(length = 100)
    private String modelo;

    @Column(length = 100)
    private String marca;

    @Column(name = "ano_fabricacao")
    private Integer anoFabricacao;

    @Column(name = "capacidade_carga", precision = 10, scale = 2)
    private BigDecimal capacidadeCarga;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private StatusCaminhao status = StatusCaminhao.ATIVO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linha_padrao_id")
    private Linha linhaPadrao;
}
