package com.r_cechim.Controle.service;

import com.suaempresa.app.model.LancamentoFinanceiro;
import com.suaempresa.app.model.enums.StatusLancamento;
import com.suaempresa.app.repository.LancamentoFinanceiroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Rotina de fechamento de fatura: lista os lançamentos ESTIMADO de um cartão
 * no período da fatura, para revisão, e permite confirmar o valorReal de cada um
 * (mudando o status para CONFIRMADO). O valorEstimado original é preservado
 * para o histórico de comparação estimado vs. real.
 */
@Service
public class FechamentoFaturaService {

    private final LancamentoFinanceiroRepository lancamentoFinanceiroRepository;

    public FechamentoFaturaService(LancamentoFinanceiroRepository lancamentoFinanceiroRepository) {
        this.lancamentoFinanceiroRepository = lancamentoFinanceiroRepository;
    }

    public List<LancamentoFinanceiro> listarPendentesDoFechamento(
            Long cartaoCreditoId, LocalDateTime inicioPeriodo, LocalDateTime fimPeriodo) {

        return lancamentoFinanceiroRepository.findByCartaoCreditoIdAndStatusAndDataBetween(
                cartaoCreditoId, StatusLancamento.ESTIMADO, inicioPeriodo, fimPeriodo);
    }

    @Transactional
    public LancamentoFinanceiro confirmarValorReal(Long lancamentoId, BigDecimal valorReal) {
        LancamentoFinanceiro lancamento = lancamentoFinanceiroRepository.findById(lancamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Lançamento não encontrado: " + lancamentoId));

        lancamento.setValorReal(valorReal);
        lancamento.setStatus(StatusLancamento.CONFIRMADO);

        return lancamentoFinanceiroRepository.save(lancamento);
    }

    /**
     * Confirma em lote todos os lançamentos pendentes de um cartão no período,
     * usando o próprio valorEstimado como valorReal — útil quando o valor bateu certinho.
     */
    @Transactional
    public void confirmarTodosComValorEstimado(Long cartaoCreditoId, LocalDateTime inicioPeriodo, LocalDateTime fimPeriodo) {
        List<LancamentoFinanceiro> pendentes = listarPendentesDoFechamento(cartaoCreditoId, inicioPeriodo, fimPeriodo);

        pendentes.forEach(lancamento -> {
            lancamento.setValorReal(lancamento.getValorEstimado());
            lancamento.setStatus(StatusLancamento.CONFIRMADO);
        });

        lancamentoFinanceiroRepository.saveAll(pendentes);
    }
}
