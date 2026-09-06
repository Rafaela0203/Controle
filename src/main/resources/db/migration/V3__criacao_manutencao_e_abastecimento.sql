-- V3: Manutenções e abastecimentos da frota

CREATE TABLE manutencao (
                            id BIGSERIAL PRIMARY KEY,
                            caminhao_id BIGINT NOT NULL REFERENCES caminhao(id),
                            tipo VARCHAR(20) NOT NULL
                                CHECK (tipo IN ('PREVENTIVA', 'CORRETIVA')),
                            descricao TEXT,
                            data_manutencao TIMESTAMP NOT NULL,
                            valor_gasto NUMERIC(12,2) NOT NULL,
                            quilometragem_no_momento NUMERIC(10,1),
                            oficina VARCHAR(150)
);

CREATE INDEX idx_manutencao_caminhao ON manutencao(caminhao_id);
CREATE INDEX idx_manutencao_data ON manutencao(data_manutencao);

-- fornecedor não é armazenado diretamente aqui: para saber qual empresa fornecedora abasteceu,
-- busca-se em caminhao_fornecedor o vínculo vigente na data_abastecimento.
CREATE TABLE abastecimento (
                               id BIGSERIAL PRIMARY KEY,
                               caminhao_id BIGINT NOT NULL REFERENCES caminhao(id),
                               data_abastecimento TIMESTAMP NOT NULL,
                               litros NUMERIC(10,2) NOT NULL,
                               valor_litro NUMERIC(10,3) NOT NULL,
                               valor_total NUMERIC(12,2) NOT NULL,
                               quilometragem_no_momento NUMERIC(10,1),
                               posto VARCHAR(150)
);

CREATE INDEX idx_abastecimento_caminhao ON abastecimento(caminhao_id);
CREATE INDEX idx_abastecimento_data ON abastecimento(data_abastecimento);