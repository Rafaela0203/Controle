package com.r_cechim.Controle.repository;

import com.r_cechim.Controle.model.Abastecimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AbastecimentoRepository extends JpaRepository<Abastecimento, Long> {

    List<Abastecimento> findByCaminhaoId(Long caminhaoId);

    List<Abastecimento> findByCaminhaoIdAndDataAbastecimentoBetween(
            Long caminhaoId, LocalDateTime inicio, LocalDateTime fim);

    /**
     * Últimos abastecimentos de um caminhão, do mais recente pro mais antigo —
     * útil para calcular consumo médio (km/l) entre dois abastecimentos seguidos.
     */
    List<Abastecimento> findByCaminhaoIdOrderByDataAbastecimentoDesc(Long caminhaoId);
}
