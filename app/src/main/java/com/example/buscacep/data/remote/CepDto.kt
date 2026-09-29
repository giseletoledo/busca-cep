package com.example.buscacep.data.remote

import com.google.gson.annotations.SerializedName

data class CepDto(
    @SerializedName("cep") val cep: String? = null,
    @SerializedName("logradouro") val logradouro: String? = null,
    @SerializedName("bairro") val bairro: String? = null,
    @SerializedName("localidade") val localidade: String? = null,
    @SerializedName("uf") val uf: String? = null,
    @SerializedName("ddd") val ddd: String? = null,
    @SerializedName("erro") val erro: Boolean? = false
)