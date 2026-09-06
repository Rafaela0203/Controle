package com.r_cechim.Controle.repository;

import com.r_cechim.Controle.model.LancamentoFinanceiro;
import com.r_cechim.Controle.model.enums.StatusLancamento;
import com.r_cechim.Controle.model.enums.TipoLancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface LancamentoFinanceiroRepository extends JpaRepository<LancamentoFinanceiro, Long> {

    List<LancamentoFinanceiro> findByDataBetween(LocalDateTime inicio, LocalDateTime fim);

    List<LancamentoFinanceiro> findByTipoAndDataBetween(
            TipoLancamento tipo, LocalDateTime inicio, LocalDateTime fim);

    /**
     * Lançamentos ainda não confirmados (ESTIMADO) de um cartão — usado na rotina
     * de fechamento de fatura, para revisar e preencher o valorReal em lote.
     */
    List<LancamentoFinanceiro> findByCartaoCreditoIdAndStatusAndDataBetween(
            Long cartaoCreditoId, StatusLancamento status, LocalDateTime inicio, LocalDateTime fim);

    List<LancamentoFinanceiro> findByCompraParceladaId(Long compraParceladaId);

    List<LancamentoFinanceiro> findByCaminhaoIdAndDataBetween(
            Long caminhaoId, LocalDateTime inicio, LocalDateTime fim);

    /**
     * Total estimado no período, por tipo (RECEITA/DESPESA) — para o relatório mensal.
     */
    @Query("SELECT COALESCE(SUM(l.valorEstimado), 0) FROM LancamentoFinanceiro l " +
           "WHERE l.tipo = :tipo AND l.data BETWEEN :inicio AND :fim")
    BigDecimal somarValorEstimadoPorTipoEPeriodo(
            @Param("tipo") TipoLancamento tipo,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim);

    /**
     * Total real no período (usa valorReal quando confirmado, senão o valorEstimado
     * como melhor estimativa disponível) — para comparar estimado vs. real por mês.
     */
    @Query("SELECT COALESCE(SUM(COALESCE(l.valorReal, l.valorEstimado)), 0) FROM LancamentoFinanceiro l " +
           "WHERE l.tipo = :tipo AND l.data BETWEEN :inicio AND :fim")
    BigDecimal somarValorRealOuEstimadoPorTipoEPeriodo(
            @Param("tipo") TipoLancamento tipo,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim);
}
