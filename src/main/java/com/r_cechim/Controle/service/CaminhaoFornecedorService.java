package com.r_cechim.Controle.service;

import com.suaempresa.app.model.CaminhaoFornecedor;
import com.suaempresa.app.repository.CaminhaoFornecedorRepository;
import com.suaempresa.app.repository.CaminhaoRepository;
import com.suaempresa.app.repository.EmpresaFornecedoraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Gerencia o histórico de vínculo entre caminhão e empresa fornecedora.
 * Garante que só existe um vínculo ativo (dataFim nula) por caminhão por vez,
 * encerrando automaticamente o vínculo anterior ao abrir um novo.
 */
@Service
public class CaminhaoFornecedorService {

    private final CaminhaoFornecedorRepository caminhaoFornecedorRepository;
    private final CaminhaoRepository caminhaoRepository;
    private final EmpresaFornecedoraRepository empresaFornecedoraRepository;

    public CaminhaoFornecedorService(CaminhaoFornecedorRepository caminhaoFornecedorRepository,
                                      CaminhaoRepository caminhaoRepository,
                                      EmpresaFornecedoraRepository empresaFornecedoraRepository) {
        this.caminhaoFornecedorRepository = caminhaoFornecedorRepository;
        this.caminhaoRepository = caminhaoRepository;
        this.empresaFornecedoraRepository = empresaFornecedoraRepository;
    }

    @Transactional
    public CaminhaoFornecedor vincular(Long caminhaoId, Long fornecedorId, LocalDateTime dataInicio) {
        caminhaoFornecedorRepository.findByCaminhaoIdAndDataFimIsNull(caminhaoId)
                .ifPresent(vinculoAtual -> {
                    vinculoAtual.setDataFim(dataInicio);
                    caminhaoFornecedorRepository.save(vinculoAtual);
                });

        CaminhaoFornecedor novoVinculo = CaminhaoFornecedor.builder()
                .caminhao(caminhaoRepository.getReferenceById(caminhaoId))
                .fornecedor(empresaFornecedoraRepository.getReferenceById(fornecedorId))
                .dataInicio(dataInicio)
                .build();

        return caminhaoFornecedorRepository.save(novoVinculo);
    }

    @Transactional
    public void desvincular(Long caminhaoId, LocalDateTime dataFim) {
        caminhaoFornecedorRepository.findByCaminhaoIdAndDataFimIsNull(caminhaoId)
                .ifPresent(vinculoAtual -> {
                    vinculoAtual.setDataFim(dataFim);
                    caminhaoFornecedorRepository.save(vinculoAtual);
                });
    }
}
