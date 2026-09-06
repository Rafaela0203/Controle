package com.r_cechim.Controle.model;

import com.r_cechim.Controle.model.enums.StatusFornecedor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "empresa_fornecedora")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaFornecedora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;

    @Column(length = 20)
    private String telefone;

    @Column(length = 255)
    private String endereco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private StatusFornecedor status = StatusFornecedor.ATIVO;
}
