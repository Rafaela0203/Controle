package com.r_cechim.Controle.service;

import com.suaempresa.app.model.CartaoCredito;
import com.suaempresa.app.model.CompraParcelada;
import com.suaempresa.app.model.LancamentoFinanceiro;
import com.suaempresa.app.model.enums.CategoriaLancamento;
import com.suaempresa.app.model.enums.TipoLancamento;
import com.suaempresa.app.repository.CompraParceladaRepository;
import com.suaempresa.app.repository.LancamentoFinanceiroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Registra uma compra parcelada e gera automaticamente um LancamentoFinanceiro
 * (categoria DESPESA) para cada parcela, um por mês, com valorEstimado preenchido
 * e status ESTIMADO até a confirmação (ex: no fechamento da fatura do cartão).
 */
@Service
public class CompraParceladaService {

    private final CompraParceladaRepository compraParceladaRepository;
    private final LancamentoFinanceiroRepository lancamentoFinanceiroRepository;

    public CompraParceladaService(CompraParceladaRepository compraParceladaRepository,
                                   LancamentoFinanceiroRepository lancamentoFinanceiroRepository) {
        this.compraParceladaRepository = compraParceladaRepository;
        this.lancamentoFinanceiroRepository = lancamentoFinanceiroRepository;
    }

    /**
     * Registra a compra parcelada e já gera os lançamentos financeiros de cada parcela.
     * O parâmetro "compra" deve chegar com cartaoCredito já setado (via getReferenceById),
     * caso seja uma compra no cartão — fica null se for parcelamento fora do cartão.
     */
    @Transactional
    public CompraParcelada registrarCompra(CompraParcelada compra) {
        CompraParcelada compraSalva = compraParceladaRepository.save(compra);
        gerarParcelas(compraSalva);
        return compraSalva;
    }

    private void gerarParcelas(CompraParcelada compra) {
        BigDecimal valorParcela = compra.getValorTotal()
                .divide(BigDecimal.valueOf(compra.getQuantidadeParcelas()), 2, RoundingMode.HALF_UP);

        CartaoCredito cartao = compra.getCartaoCredito();

        for (int numeroParcela = 1; numeroParcela <= compra.getQuantidadeParcelas(); numeroParcela++) {
            // plusMonths já ajusta automaticamente meses com menos dias (ex: dia 31 -> 28/29 em fevereiro)
            var dataParcela = compra.getDataCompra().plusMonths(numeroParcela - 1L);

            LancamentoFinanceiro parcela = LancamentoFinanceiro.builder()
                    .tipo(TipoLancamento.DESPESA)
                    .categoria(CategoriaLancamento.OUTROS)
                    .descricao(compra.getDescricao() + " - parcela " + numeroParcela + "/" + compra.getQuantidadeParcelas())
                    .data(dataParcela)
                    .valorEstimado(valorParcela)
                    .cartaoCredito(cartao)
                    .compraParcelada(compra)
                    .numeroParcela(numeroParcela)
                    .totalParcelas(compra.getQuantidadeParcelas())
                    .build();

            lancamentoFinanceiroRepository.save(parcela);
        }
    }
}
