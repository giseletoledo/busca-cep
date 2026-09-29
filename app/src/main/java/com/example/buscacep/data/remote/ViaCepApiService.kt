package com.example.buscacep.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepApiService {
    @GET("ws/{cep}/json/")
    suspend fun buscarCep(@Path("cep") cep: String): Response<CepDto>
}