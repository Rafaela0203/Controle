package com.r_cechim.Controle.service;

import com.suaempresa.app.model.enums.TipoLancamento;
import com.suaempresa.app.repository.LancamentoFinanceiroRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

/**
 * Comparação estimado vs. real, por mês e por tipo (RECEITA/DESPESA).
 */
@Service
public class RelatorioFinanceiroService {

    private final LancamentoFinanceiroRepository lancamentoFinanceiroRepository;

    public RelatorioFinanceiroService(LancamentoFinanceiroRepository lancamentoFinanceiroRepository) {
        this.lancamentoFinanceiroRepository = lancamentoFinanceiroRepository;
    }

    public ResumoMensal resumoDoMes(YearMonth mes, TipoLancamento tipo) {
        LocalDateTime inicio = mes.atDay(1).atStartOfDay();
        LocalDateTime fim = mes.atEndOfMonth().atTime(23, 59, 59);

        BigDecimal totalEstimado = lancamentoFinanceiroRepository
                .somarValorEstimadoPorTipoEPeriodo(tipo, inicio, fim);

        BigDecimal totalRealOuEstimado = lancamentoFinanceiroRepository
                .somarValorRealOuEstimadoPorTipoEPeriodo(tipo, inicio, fim);

        return new ResumoMensal(mes, tipo, totalEstimado, totalRealOuEstimado);
    }

    public record ResumoMensal(
            YearMonth mes,
            TipoLancamento tipo,
            BigDecimal totalEstimado,
            BigDecimal totalRealOuEstimado
    ) {
        public BigDecimal diferenca() {
            return totalRealOuEstimado.subtract(totalEstimado);
        }
    }
}
