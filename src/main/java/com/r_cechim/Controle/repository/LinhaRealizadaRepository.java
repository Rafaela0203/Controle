package com.r_cechim.Controle.repository;

import com.r_cechim.Controle.model.LinhaRealizada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LinhaRealizadaRepository extends JpaRepository<LinhaRealizada, Long> {

    List<LinhaRealizada> findByCaminhaoIdAndDataRealizacaoBetween(
            Long caminhaoId, LocalDateTime inicio, LocalDateTime fim);

    List<LinhaRealizada> findByLinhaId(Long linhaId);

    /**
     * Linhas realizadas que fugiram do padrão do caminhão — útil para relatório de exceções.
     */
    List<LinhaRealizada> findByFoiLinhaPadraoFalseAndDataRealizacaoBetween(
            LocalDateTime inicio, LocalDateTime fim);
}
