-- V4: Empresas clientes e valores de frete (com histórico de vigência)

CREATE TABLE empresa_cliente (
                                 id BIGSERIAL PRIMARY KEY,
                                 nome VARCHAR(150) NOT NULL,
                                 cpf_cnpj VARCHAR(18) NOT NULL UNIQUE,
                                 telefone VARCHAR(20),
                                 endereco VARCHAR(255)
);

CREATE TABLE tabela_frete (
                              id BIGSERIAL PRIMARY KEY,
                              empresa_cliente_id BIGINT NOT NULL REFERENCES empresa_cliente(id),
                              descricao VARCHAR(150),
                              tipo_calculo VARCHAR(20) NOT NULL
                                  CHECK (tipo_calculo IN ('POR_LITRO', 'FIXO')),
                              valor_por_litro NUMERIC(10,4),
                              valor_fixo NUMERIC(12,2),
                              data_inicio_vigencia TIMESTAMP NOT NULL,
                              data_fim_vigencia TIMESTAMP, -- null = vigente atualmente
                              CONSTRAINT chk_valor_conforme_tipo CHECK (
                                  (tipo_calculo = 'POR_LITRO' AND valor_por_litro IS NOT NULL)
                                      OR
                                  (tipo_calculo = 'FIXO' AND valor_fixo IS NOT NULL)
                                  )
);

CREATE INDEX idx_tabela_frete_empresa ON tabela_frete(empresa_cliente_id);
-- Garante só uma tabela de frete vigente (data_fim_vigencia nula) por empresa+descricao
CREATE UNIQUE INDEX idx_tabela_frete_vigente
    ON tabela_frete(empresa_cliente_id, descricao)
    WHERE data_fim_vigencia IS NULL;