-- V1: Tabelas base (sem dependências entre si, exceto caminhao -> linha)

CREATE TABLE empresa_fornecedora (
                                     id BIGSERIAL PRIMARY KEY,
                                     nome VARCHAR(150) NOT NULL,
                                     cnpj VARCHAR(18) NOT NULL UNIQUE,
                                     telefone VARCHAR(20),
                                     endereco VARCHAR(255),
                                     status VARCHAR(20) NOT NULL DEFAULT 'ATIVO'
                                         CHECK (status IN ('ATIVO', 'INATIVO'))
);

CREATE TABLE linha (
                       id BIGSERIAL PRIMARY KEY,
                       nome VARCHAR(150) NOT NULL,
                       origem VARCHAR(150),
                       destino VARCHAR(150),
                       distancia_km NUMERIC(10,2),
                       observacoes TEXT
);

CREATE TABLE caminhao (
                          id BIGSERIAL PRIMARY KEY,
                          placa VARCHAR(10) NOT NULL UNIQUE,
                          modelo VARCHAR(100),
                          marca VARCHAR(100),
                          ano_fabricacao INTEGER,
                          capacidade_carga NUMERIC(10,2),
                          status VARCHAR(20) NOT NULL DEFAULT 'ATIVO'
                              CHECK (status IN ('ATIVO', 'MANUTENCAO', 'INATIVO')),
                          linha_padrao_id BIGINT REFERENCES linha(id)
);

CREATE INDEX idx_caminhao_linha_padrao ON caminhao(linha_padrao_id);