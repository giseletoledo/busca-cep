package com.example.buscacep.data.repository

import com.example.buscacep.data.local.CepDao
import com.example.buscacep.data.local.CepEntity
import com.example.buscacep.data.remote.RetrofitProvider
import com.example.buscacep.data.remote.ViaCepApiService
import com.example.buscacep.domain.model.BuscaEnderecoResultado
import com.example.buscacep.domain.model.toDomainOrNull
import com.example.buscacep.domain.repository.CepRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CepRepositoryImpl(
    private val cepDao: CepDao,
    private val api: ViaCepApiService = RetrofitProvider.getInstance()
) : CepRepository {

    override suspend fun saveCep(cep: String) {
        cepDao.insertCep(CepEntity(cep = cep))
    }

    override suspend fun getAllCeps(): List<String> {
        return cepDao.getAllCeps().map { it.cep }
    }

    override suspend fun buscarEndereco(cep: String): BuscaEnderecoResultado =
        withContext(Dispatchers.IO) {
            try {
                val response = api.buscarCep(cep)
                val dto = response.body()
                when {
                    !response.isSuccessful || dto == null ->
                        BuscaEnderecoResultado.Falha("Erro ${response.code()}")
                    else -> dto.toDomainOrNull()?.let { BuscaEnderecoResultado.Sucesso(it) }
                        ?: BuscaEnderecoResultado.NaoEncontrado
                }
            } catch (e: Exception) {
                BuscaEnderecoResultado.Falha(e.localizedMessage ?: "Falha de conexão")
            }
        }
}