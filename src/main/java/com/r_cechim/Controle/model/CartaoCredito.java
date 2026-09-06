package com.r_cechim.Controle.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cartao_credito")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartaoCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "dia_fechamento", nullable = false)
    private Integer diaFechamento;

    @Column(name = "dia_vencimento")
    private Integer diaVencimento;
}
