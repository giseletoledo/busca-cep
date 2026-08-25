package com.example.buscacep.domain.repository

interface CepRepository {
    suspend fun saveCep(cep: String)
    suspend fun getAllCeps(): List<String>
}