package com.example.buscacep.data.repository

import com.example.buscacep.data.local.CepDao
import com.example.buscacep.data.local.CepEntity
import com.example.buscacep.domain.repository.CepRepository

class CepRepositoryImpl(
    private val cepDao: CepDao
) : CepRepository {

    override suspend fun saveCep(cep: String) {
        cepDao.insertCep(CepEntity(cep = cep))
    }

    override suspend fun getAllCeps(): List<String> {
        return cepDao.getAllCeps().map { it.cep }
    }
}