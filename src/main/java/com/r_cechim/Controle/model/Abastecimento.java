package com.r_cechim.Controle.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * A empresa fornecedora responsável não é armazenada aqui diretamente:
 * é obtida via CaminhaoFornecedor, buscando o vínculo vigente em dataAbastecimento.
 */
@Entity
@Table(name = "abastecimento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Abastecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "caminhao_id", nullable = false)
    private Caminhao caminhao;

    @Column(name = "data_abastecimento", nullable = false)
    private LocalDateTime dataAbastecimento;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal litros;

    @Column(name = "valor_litro", nullable = false, precision = 10, scale = 3)
    private BigDecimal valorLitro;

    @Column(name = "valor_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "quilometragem_no_momento", precision = 10, scale = 1)
    private BigDecimal quilometragemNoMomento;

    @Column(length = 150)
    private String posto;
}
