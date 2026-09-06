package com.r_cechim.Controle.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "empresa_cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(name = "cpf_cnpj", nullable = false, unique = true, length = 18)
    private String cpfCnpj;

    @Column(length = 20)
    private String telefone;

    @Column(length = 255)
    private String endereco;
}
