package com.r_cechim.Controle.repository;

import com.r_cechim.Controle.model.EmpresaFornecedora;
import com.r_cechim.Controle.model.enums.StatusFornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmpresaFornecedoraRepository extends JpaRepository<EmpresaFornecedora, Long> {

    Optional<EmpresaFornecedora> findByCnpj(String cnpj);

    List<EmpresaFornecedora> findByStatus(StatusFornecedor status);
}
