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
 * Registro de uma linha efetivamente realizada por um caminhão.
 * A empresa fornecedora responsável não é armazenada aqui diretamente:
 * é obtida via CaminhaoFornecedor, buscando o vínculo vigente em dataRealizacao.
 */
@Entity
@Table(name = "linha_realizada")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinhaRealizada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "caminhao_id", nullable = false)
    private Caminhao caminhao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "linha_id", nullable = false)
    private Linha linha;

    @Column(name = "data_realizacao", nullable = false)
    private LocalDateTime dataRealizacao;

    @Column(name = "foi_linha_padrao", nullable = false)
    @Builder.Default
    private boolean foiLinhaPadrao = true;

    @Column(name = "quilometragem_inicial", precision = 10, scale = 1)
    private BigDecimal quilometragemInicial;

    @Column(name = "quilometragem_final", precision = 10, scale = 1)
    private BigDecimal quilometragemFinal;

    @Column(columnDefinition = "TEXT")
    private String observacoes;
}
