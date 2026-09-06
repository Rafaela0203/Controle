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
 * Registro diário de coleta de leite: volume lançado pelo fornecedor (motorista terceirizado)
 * vs. volume conferido na balança no recebimento pela empresa cliente.
 * A empresa fornecedora responsável não é armazenada aqui diretamente:
 * é obtida via CaminhaoFornecedor, buscando o vínculo vigente na data da coleta.
 */
@Entity
@Table(name = "coleta_leite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColetaLeite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime data;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "caminhao_id", nullable = false)
    private Caminhao caminhao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_cliente_id", nullable = false)
    private EmpresaCliente empresaCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linha_id")
    private Linha linha;

    @Column(name = "volume_lancado_fornecedor", nullable = false, precision = 12, scale = 2)
    private BigDecimal volumeLancadoFornecedor;

    @Column(name = "volume_balanca", precision = 12, scale = 2)
    private BigDecimal volumeBalanca;

    // Coluna gerada pelo banco (GENERATED ALWAYS AS ... STORED); somente leitura no Java.
    @Column(name = "diferenca", precision = 12, scale = 2, insertable = false, updatable = false)
    private BigDecimal diferenca;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tabela_frete_id", nullable = false)
    private TabelaFrete tabelaFrete;

    @Column(name = "valor_frete_calculado", precision = 12, scale = 2)
    private BigDecimal valorFreteCalculado;

    @Column(columnDefinition = "TEXT")
    private String observacoes;
}
