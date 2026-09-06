package com.r_cechim.Controle.repository;

import com.r_cechim.Controle.model.Linha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinhaRepository extends JpaRepository<Linha, Long> {

    List<Linha> findByNomeContainingIgnoreCase(String nome);
}
