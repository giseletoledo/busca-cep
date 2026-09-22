package com.example.buscacep.flow
data class CepUiStateFlow(
    val ceps: List<String> = emptyList(),   // ajuste ao tipo real de CepEntity/model
    val cepAtual: String = "",
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null
)