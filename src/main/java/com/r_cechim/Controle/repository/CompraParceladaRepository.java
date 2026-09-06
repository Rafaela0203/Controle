package com.r_cechim.Controle.repository;

import com.r_cechim.Controle.model.CompraParcelada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompraParceladaRepository extends JpaRepository<CompraParcelada, Long> {

    List<CompraParcelada> findByCartaoCreditoId(Long cartaoCreditoId);
}
