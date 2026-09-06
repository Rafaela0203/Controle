package com.r_cechim.Controle.repository;

import com.r_cechim.Controle.model.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {

    List<Manutencao> findByCaminhaoId(Long caminhaoId);

    List<Manutencao> findByCaminhaoIdAndDataManutencaoBetween(
            Long caminhaoId, LocalDateTime inicio, LocalDateTime fim);

    List<Manutencao> findByDataManutencaoBetween(LocalDateTime inicio, LocalDateTime fim);
}
