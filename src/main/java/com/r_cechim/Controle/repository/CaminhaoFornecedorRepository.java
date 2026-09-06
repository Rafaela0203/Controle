package com.r_cechim.Controle.repository;

import com.r_cechim.Controle.model.CaminhaoFornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CaminhaoFornecedorRepository extends JpaRepository<CaminhaoFornecedor, Long> {

    /**
     * Vínculo atualmente ativo (dataFim nula) de um caminhão.
     */
    Optional<CaminhaoFornecedor> findByCaminhaoIdAndDataFimIsNull(Long caminhaoId);

    /**
     * Histórico completo de fornecedores de um caminhão, do mais recente pro mais antigo.
     */
    List<CaminhaoFornecedor> findByCaminhaoIdOrderByDataInicioDesc(Long caminhaoId);

    /**
     * Descobre qual empresa fornecedora estava vinculada a um caminhão em uma data específica.
     * Usado para achar o fornecedor responsável em LinhaRealizada, Abastecimento, ColetaLeite
     * e LancamentoFinanceiro, já que essas tabelas não guardam o fornecedor diretamente.
     */
    @Query("SELECT cf FROM CaminhaoFornecedor cf " +
           "WHERE cf.caminhao.id = :caminhaoId " +
           "AND cf.dataInicio <= :dataReferencia " +
           "AND (cf.dataFim IS NULL OR cf.dataFim >= :dataReferencia)")
    Optional<CaminhaoFornecedor> findVinculoVigente(
            @Param("caminhaoId") Long caminhaoId,
            @Param("dataReferencia") LocalDateTime dataReferencia);
}
