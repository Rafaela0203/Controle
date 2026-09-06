package com.r_cechim.Controle.service;

import com.suaempresa.app.model.ColetaLeite;
import com.suaempresa.app.model.LancamentoFinanceiro;
import com.suaempresa.app.model.TabelaFrete;
import com.suaempresa.app.model.enums.CategoriaLancamento;
import com.suaempresa.app.model.enums.TipoCalculoFrete;
import com.suaempresa.app.model.enums.TipoLancamento;
import com.suaempresa.app.repository.ColetaLeiteRepository;
import com.suaempresa.app.repository.LancamentoFinanceiroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Registra uma coleta de leite, calcula o valor do frete conforme a TabelaFrete
 * vinculada (por litro ou fixo) e gera automaticamente a receita correspondente
 * em LancamentoFinanceiro, evitando lançamento manual depois.
 */
@Service
public class ColetaLeiteService {

    private final ColetaLeiteRepository coletaLeiteRepository;
    private final LancamentoFinanceiroRepository lancamentoFinanceiroRepository;

    public ColetaLeiteService(ColetaLeiteRepository coletaLeiteRepository,
                               LancamentoFinanceiroRepository lancamentoFinanceiroRepository) {
        this.coletaLeiteRepository = coletaLeiteRepository;
        this.lancamentoFinanceiroRepository = lancamentoFinanceiroRepository;
    }

    @Transactional
    public ColetaLeite registrarColeta(ColetaLeite coleta) {
        coleta.setValorFreteCalculado(calcularValorFrete(coleta));
        ColetaLeite coletaSalva = coletaLeiteRepository.save(coleta);
        gerarReceitaFrete(coletaSalva);
        return coletaSalva;
    }

    private java.math.BigDecimal calcularValorFrete(ColetaLeite coleta) {
        TabelaFrete tabelaFrete = coleta.getTabelaFrete();

        if (tabelaFrete.getTipoCalculo() == TipoCalculoFrete.FIXO) {
            return tabelaFrete.getValorFixo();
        }

        // POR_LITRO: usa o volume da balança (conferido no recebimento) como referência de cobrança
        java.math.BigDecimal volumeCobrado = coleta.getVolumeBalanca() != null
                ? coleta.getVolumeBalanca()
                : coleta.getVolumeLancadoFornecedor();

        return tabelaFrete.getValorPorLitro().multiply(volumeCobrado);
    }

    private void gerarReceitaFrete(ColetaLeite coleta) {
        LancamentoFinanceiro receita = LancamentoFinanceiro.builder()
                .tipo(TipoLancamento.RECEITA)
                .categoria(CategoriaLancamento.FRETE)
                .descricao("Frete - coleta de leite #" + coleta.getId())
                .data(coleta.getData())
                .valorEstimado(coleta.getValorFreteCalculado())
                .caminhao(coleta.getCaminhao())
                .empresaCliente(coleta.getEmpresaCliente())
                .coletaLeite(coleta)
                .build();

        lancamentoFinanceiroRepository.save(receita);
    }
}
