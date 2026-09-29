package com.example.buscacep.flow

import com.example.buscacep.domain.model.CepEndereco

data class CepUiStateFlow(
    val ceps: List<String> = emptyList(),   // ajuste ao tipo real de CepEntity/model
    val cepAtual: String = "",
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null,
    val endereco: CepEndereco? = null,
    val isLoadingEndereco: Boolean = false
)