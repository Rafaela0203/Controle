package com.r_cechim.Controle.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Histórico de vínculo entre um caminhão e a empresa fornecedora responsável por dirigi-lo.
 * dataFim nula = vínculo atualmente ativo.
 */
@Entity
@Table(name = "caminhao_fornecedor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaminhaoFornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "caminhao_id", nullable = false)
    private Caminhao caminhao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private EmpresaFornecedora fornecedor;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;
}
