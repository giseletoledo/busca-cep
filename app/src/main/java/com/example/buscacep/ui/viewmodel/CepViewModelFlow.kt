package com.example.buscacep.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buscacep.domain.model.CepValidator
import com.example.buscacep.domain.repository.CepRepository
import com.example.buscacep.flow.CepUiStateFlow
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CepViewModelFlow(
    private val repository: CepRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CepUiStateFlow())
    val uiState: StateFlow<CepUiStateFlow> = _uiState.asStateFlow()

    init { loadCeps() }

    fun loadCeps() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val ceps = repository.getAllCeps()
                _uiState.update { it.copy(ceps = ceps, isLoading = false) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Erro ao carregar os CEPs salvos") }
            }
        }
    }

    fun onCepChanged(newCep: String) {
        _uiState.update { it.copy(cepAtual = newCep, errorMessage = null) }
    }

    fun saveCep() {
        val cep = _uiState.value.cepAtual
        if (!CepValidator.isValid(cep)) {
            _uiState.update { it.copy(errorMessage = "CEP inválido. Digite 8 números.") }
            return
        }
        viewModelScope.launch {
            try {
                repository.saveCep(cep)
                _uiState.update { it.copy(cepAtual = "", isSaved = true) }
                loadCeps()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Erro ao salvar o CEP") }
            }
        }
    }

    fun onSavedMessageShown() { _uiState.update { it.copy(isSaved = false) } }
    fun onErrorMessageShown() { _uiState.update { it.copy(errorMessage = null) } }
}