package com.example.buscacep.domain.model

import com.example.buscacep.data.remote.CepDto
data class CepEndereco(
    val cep: String,
    val logradouro: String,
    val bairro: String,
    val cidade: String,
    val uf: String,
    val ddd: String
)

sealed class BuscaEnderecoResultado {
    data class Sucesso(val endereco: CepEndereco) : BuscaEnderecoResultado()
    object NaoEncontrado : BuscaEnderecoResultado()
    data class Falha(val mensagem: String) : BuscaEnderecoResultado()
}

fun CepDto.toDomainOrNull(): CepEndereco? {
    if (erro == true) return null
    return CepEndereco(
        cep = cep.orEmpty(), logradouro = logradouro.orEmpty(), bairro = bairro.orEmpty(),
        cidade = localidade.orEmpty(), uf = uf.orEmpty(), ddd = ddd.orEmpty()
    )
}