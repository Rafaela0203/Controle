-- V2: Histórico de vínculo caminhão-fornecedor e registro de linhas realizadas

CREATE TABLE caminhao_fornecedor (
                                     id BIGSERIAL PRIMARY KEY,
                                     caminhao_id BIGINT NOT NULL REFERENCES caminhao(id),
                                     fornecedor_id BIGINT NOT NULL REFERENCES empresa_fornecedora(id),
                                     data_inicio TIMESTAMP NOT NULL,
                                     data_fim TIMESTAMP -- null = vínculo atual/ativo
);

CREATE INDEX idx_caminhao_fornecedor_caminhao ON caminhao_fornecedor(caminhao_id);
CREATE INDEX idx_caminhao_fornecedor_fornecedor ON caminhao_fornecedor(fornecedor_id);
-- Garante que só existe um vínculo ativo (data_fim nula) por caminhão por vez
CREATE UNIQUE INDEX idx_caminhao_fornecedor_vinculo_ativo
    ON caminhao_fornecedor(caminhao_id)
    WHERE data_fim IS NULL;

-- fornecedor não é armazenado diretamente aqui: para saber qual empresa fornecedora
-- estava dirigindo, busca-se em caminhao_fornecedor o vínculo vigente na data_realizacao.
CREATE TABLE linha_realizada (
                                 id BIGSERIAL PRIMARY KEY,
                                 caminhao_id BIGINT NOT NULL REFERENCES caminhao(id),
                                 linha_id BIGINT NOT NULL REFERENCES linha(id),
                                 data_realizacao TIMESTAMP NOT NULL,
                                 foi_linha_padrao BOOLEAN NOT NULL DEFAULT true,
                                 quilometragem_inicial NUMERIC(10,1),
                                 quilometragem_final NUMERIC(10,1),
                                 observacoes TEXT
);

CREATE INDEX idx_linha_realizada_caminhao ON linha_realizada(caminhao_id);
CREATE INDEX idx_linha_realizada_linha ON linha_realizada(linha_id);
CREATE INDEX idx_linha_realizada_data ON linha_realizada(data_realizacao);