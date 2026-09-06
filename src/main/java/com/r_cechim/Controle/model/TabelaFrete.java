package com.r_cechim.Controle.model;

import com.r_cechim.Controle.model.enums.TipoCalculoFrete;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Valor de frete vigente para uma empresa cliente. Pode haver mais de um
 * registro por empresa (histórico de vigência); dataFimVigencia nula = vigente.
 */
@Entity
@Table(name = "tabela_frete")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TabelaFrete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_cliente_id", nullable = false)
    private EmpresaCliente empresaCliente;

    @Column(length = 150)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_calculo", nullable = false, length = 20)
    private TipoCalculoFrete tipoCalculo;

    @Column(name = "valor_por_litro", precision = 10, scale = 4)
    private BigDecimal valorPorLitro;

    @Column(name = "valor_fixo", precision = 12, scale = 2)
    private BigDecimal valorFixo;

    @Column(name = "data_inicio_vigencia", nullable = false)
    private LocalDateTime dataInicioVigencia;

    @Column(name = "data_fim_vigencia")
    private LocalDateTime dataFimVigencia;
}
