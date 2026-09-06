package com.r_cechim.Controle.model;

import com.r_cechim.Controle.model.enums.TipoManutencao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "manutencao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "caminhao_id", nullable = false)
    private Caminhao caminhao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoManutencao tipo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_manutencao", nullable = false)
    private LocalDateTime dataManutencao;

    @Column(name = "valor_gasto", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorGasto;

    @Column(name = "quilometragem_no_momento", precision = 10, scale = 1)
    private BigDecimal quilometragemNoMomento;

    @Column(length = 150)
    private String oficina;
}
