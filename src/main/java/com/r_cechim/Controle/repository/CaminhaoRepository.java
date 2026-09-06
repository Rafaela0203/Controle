package com.r_cechim.Controle.repository;

import com.r_cechim.Controle.model.Caminhao;
import com.r_cechim.Controle.model.enums.StatusCaminhao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CaminhaoRepository extends JpaRepository<Caminhao, Long> {

    Optional<Caminhao> findByPlaca(String placa);

    List<Caminhao> findByStatus(StatusCaminhao status);
}
