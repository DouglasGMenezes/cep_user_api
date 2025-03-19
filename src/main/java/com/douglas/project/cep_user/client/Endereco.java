package com.douglas.project.cep_user.client;

public record Endereco(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String estado,
        String uf
) {
}
