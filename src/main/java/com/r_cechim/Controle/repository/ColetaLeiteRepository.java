package com.r_cechim.Controle.repository;

import com.r_cechim.Controle.model.ColetaLeite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ColetaLeiteRepository extends JpaRepository<ColetaLeite, Long> {

    List<ColetaLeite> findByDataBetween(LocalDateTime inicio, LocalDateTime fim);

    List<ColetaLeite> findByCaminhaoIdAndDataBetween(
            Long caminhaoId, LocalDateTime inicio, LocalDateTime fim);

    List<ColetaLeite> findByEmpresaClienteIdAndDataBetween(
            Long empresaClienteId, LocalDateTime inicio, LocalDateTime fim);

    /**
     * Coletas com diferença relevante entre o volume lançado pelo fornecedor e o
     * volume da balança — útil para relatório de quebra/sobra.
     */
    List<ColetaLeite> findByDiferencaNotAndDataBetween(
            java.math.BigDecimal diferenca, LocalDateTime inicio, LocalDateTime fim);
}
