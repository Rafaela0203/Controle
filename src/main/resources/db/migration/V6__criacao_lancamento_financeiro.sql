-- V6: Cartões de crédito, compras parceladas e financeiro completo (com estimado/real)

CREATE TABLE cartao_credito (
                                id BIGSERIAL PRIMARY KEY,
                                nome VARCHAR(100) NOT NULL,
                                dia_fechamento INTEGER NOT NULL CHECK (dia_fechamento BETWEEN 1 AND 31),
                                dia_vencimento INTEGER CHECK (dia_vencimento BETWEEN 1 AND 31)
);

CREATE TABLE compra_parcelada (
                                  id BIGSERIAL PRIMARY KEY,
                                  descricao VARCHAR(255) NOT NULL,
                                  data_compra TIMESTAMP NOT NULL,
                                  valor_total NUMERIC(12,2) NOT NULL,
                                  quantidade_parcelas INTEGER NOT NULL CHECK (quantidade_parcelas > 0),
                                  cartao_credito_id BIGINT REFERENCES cartao_credito(id) -- opcional: parcelamento fora do cartão (ex: financiamento) fica null
);

CREATE INDEX idx_compra_parcelada_cartao ON compra_parcelada(cartao_credito_id);

-- fornecedor não é armazenado diretamente aqui: para saber qual empresa fornecedora está associada
-- (ex: despesa ligada a uma empresa fornecedora específica), busca-se em caminhao_fornecedor.
CREATE TABLE lancamento_financeiro (
                                       id BIGSERIAL PRIMARY KEY,
                                       tipo VARCHAR(10) NOT NULL
                                           CHECK (tipo IN ('RECEITA', 'DESPESA')),
                                       categoria VARCHAR(20) NOT NULL
                                           CHECK (categoria IN ('FRETE', 'MANUTENCAO', 'COMBUSTIVEL', 'SALARIO', 'IMPOSTO', 'ALUGUEL', 'OUTROS')),
                                       descricao VARCHAR(255),
                                       data TIMESTAMP NOT NULL,

                                       valor_estimado NUMERIC(12,2) NOT NULL,
                                       valor_real NUMERIC(12,2), -- null até ser confirmado
                                       status VARCHAR(20) NOT NULL DEFAULT 'ESTIMADO'
                                           CHECK (status IN ('ESTIMADO', 'CONFIRMADO')),

                                       cartao_credito_id BIGINT REFERENCES cartao_credito(id),
                                       compra_parcelada_id BIGINT REFERENCES compra_parcelada(id),
                                       numero_parcela INTEGER,
                                       total_parcelas INTEGER,

                                       caminhao_id BIGINT REFERENCES caminhao(id),
                                       empresa_cliente_id BIGINT REFERENCES empresa_cliente(id),
                                       coleta_leite_id BIGINT REFERENCES coleta_leite(id),
                                       forma_pagamento VARCHAR(20)
                                           CHECK (forma_pagamento IN ('DINHEIRO', 'PIX', 'BOLETO', 'CARTAO', 'TRANSFERENCIA'))
);

CREATE INDEX idx_lancamento_data ON lancamento_financeiro(data);
CREATE INDEX idx_lancamento_tipo_categoria ON lancamento_financeiro(tipo, categoria);
CREATE INDEX idx_lancamento_status ON lancamento_financeiro(status);
CREATE INDEX idx_lancamento_caminhao ON lancamento_financeiro(caminhao_id);
CREATE INDEX idx_lancamento_empresa ON lancamento_financeiro(empresa_cliente_id);
CREATE INDEX idx_lancamento_cartao ON lancamento_financeiro(cartao_credito_id);
CREATE INDEX idx_lancamento_compra_parcelada ON lancamento_financeiro(compra_parcelada_id);