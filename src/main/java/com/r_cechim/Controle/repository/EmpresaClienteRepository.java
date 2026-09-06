package com.r_cechim.Controle.repository;

import com.r_cechim.Controle.model.EmpresaCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaClienteRepository extends JpaRepository<EmpresaCliente, Long> {

    Optional<EmpresaCliente> findByCpfCnpj(String cpfCnpj);
}
