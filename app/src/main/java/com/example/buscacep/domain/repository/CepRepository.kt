package com.example.buscacep.domain.repository

import com.example.buscacep.domain.model.BuscaEnderecoResultado

interface CepRepository {
    suspend fun saveCep(cep: String)
    suspend fun getAllCeps(): List<String>
    suspend fun buscarEndereco(cep: String): BuscaEnderecoResultado
}