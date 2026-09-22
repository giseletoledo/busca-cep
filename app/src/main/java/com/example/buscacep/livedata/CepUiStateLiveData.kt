package com.example.buscacep.livedata

data class CepUiStateLiveData(
    val cepAtual: String = "",
    val ceps: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null
)