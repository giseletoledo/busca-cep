package com.example.buscacep.livedata

import com.example.buscacep.domain.model.CepEndereco

data class CepUiStateLiveData(
    val endereco: CepEndereco? = null,
    val isLoadingEndereco: Boolean = false,
    val cepAtual: String = "",
    val ceps: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null
)