package com.example.buscacep.ui

data class CepUiState(
    val cepAtual: String = "",
    val ceps: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null
)