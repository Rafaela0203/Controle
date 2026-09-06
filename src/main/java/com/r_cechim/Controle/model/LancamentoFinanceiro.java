package com.r_cechim.Controle.model;

import com.r_cechim.Controle.model.enums.CategoriaLancamento;
import com.r_cechim.Controle.model.enums.FormaPagamento;
import com.r_cechim.Controle.model.enums.StatusLancamento;
import com.r_cechim.Controle.model.enums.TipoLancamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Financeiro completo da empresa (receitas e despesas).
 * valorEstimado é preenchido no lançamento; valorReal fica null até a confirmação
 * (ex: fechamento da fatura do cartão), permitindo comparar estimado vs. real depois.
 * A empresa fornecedora associada (se houver) não é armazenada aqui diretamente:
 * é obtida via CaminhaoFornecedor, buscando o vínculo vigente na data do lançamento.
 */
@Entity
@Table(name = "lancamento_financeiro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LancamentoFinanceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoLancamento tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategoriaLancamento categoria;

    @Column(length = 255)
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(name = "valor_estimado", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorEstimado;

    @Column(name = "valor_real", precision = 12, scale = 2)
    private BigDecimal valorReal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private StatusLancamento status = StatusLancamento.ESTIMADO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartao_credito_id")
    private CartaoCredito cartaoCredito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compra_parcelada_id")
    private CompraParcelada compraParcelada;

    @Column(name = "numero_parcela")
    private Integer numeroParcela;

    @Column(name = "total_parcelas")
    private Integer totalParcelas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caminhao_id")
    private Caminhao caminhao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_cliente_id")
    private EmpresaCliente empresaCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coleta_leite_id")
    private ColetaLeite coletaLeite;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento", length = 20)
    private FormaPagamento formaPagamento;
}
