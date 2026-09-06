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
 * Compra parcelada (no cartão ou fora dele, ex: financiamento).
 * Ao ser salva, o Service deve gerar N LancamentoFinanceiro (um por parcela/mês),
 * cada um referenciando esta compra via compraParcelada + numeroParcela/totalParcelas.
 */
@Entity
@Table(name = "compra_parcelada")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraParcelada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(name = "data_compra", nullable = false)
    private LocalDateTime dataCompra;

    @Column(name = "valor_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "quantidade_parcelas", nullable = false)
    private Integer quantidadeParcelas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartao_credito_id")
    private CartaoCredito cartaoCredito;
}
