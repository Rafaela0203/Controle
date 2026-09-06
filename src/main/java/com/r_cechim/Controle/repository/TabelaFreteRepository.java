package com.r_cechim.Controle.repository;

import com.r_cechim.Controle.model.TabelaFrete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TabelaFreteRepository extends JpaRepository<TabelaFrete, Long> {

    List<TabelaFrete> findByEmpresaClienteId(Long empresaClienteId);

    /**
     * Tabela de frete atualmente vigente (dataFimVigencia nula) para uma empresa + descrição.
     * Como pode haver mais de uma tabela vigente por empresa (descrições diferentes,
     * ex: "Frete padrão" e "Frete rota longa"), filtra também pela descrição.
     */
    Optional<TabelaFrete> findByEmpresaClienteIdAndDescricaoAndDataFimVigenciaIsNull(
            Long empresaClienteId, String descricao);

    List<TabelaFrete> findByEmpresaClienteIdAndDataFimVigenciaIsNull(Long empresaClienteId);
}
