-- V5: Coletas de leite (registro diário por linha/caminhão)

-- fornecedor não é armazenado diretamente aqui: para saber qual empresa fornecedora dirigiu na coleta,
-- busca-se em caminhao_fornecedor o vínculo vigente na data da coleta.
CREATE TABLE coleta_leite (
                              id BIGSERIAL PRIMARY KEY,
                              data TIMESTAMP NOT NULL,
                              caminhao_id BIGINT NOT NULL REFERENCES caminhao(id),
                              empresa_cliente_id BIGINT NOT NULL REFERENCES empresa_cliente(id),
                              linha_id BIGINT REFERENCES linha(id),
                              volume_lancado_fornecedor NUMERIC(12,2) NOT NULL,
                              volume_balanca NUMERIC(12,2),
                              diferenca NUMERIC(12,2) GENERATED ALWAYS AS (volume_balanca - volume_lancado_fornecedor) STORED,
                              tabela_frete_id BIGINT NOT NULL REFERENCES tabela_frete(id),
                              valor_frete_calculado NUMERIC(12,2), -- calculado pela aplicação ao salvar/confirmar a coleta
                              observacoes TEXT
);

CREATE INDEX idx_coleta_leite_data ON coleta_leite(data);
CREATE INDEX idx_coleta_leite_caminhao ON coleta_leite(caminhao_id);
CREATE INDEX idx_coleta_leite_empresa ON coleta_leite(empresa_cliente_id);
CREATE INDEX idx_coleta_leite_tabela_frete ON coleta_leite(tabela_frete_id);