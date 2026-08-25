package com.example.buscacep.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.buscacep.domain.model.CepValidator
import com.example.buscacep.domain.repository.CepRepository
import kotlinx.coroutines.launch

class CepViewModel(
    private val repository: CepRepository
) : ViewModel() {

    private val _uiState = MutableLiveData(CepUiState())
    val uiState: LiveData<CepUiState> = _uiState

    init { loadCeps() }

    fun loadCeps() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true, errorMessage = null) }
            try {
                val ceps = repository.getAllCeps()
                updateState { it.copy(ceps = ceps, isLoading = false) }
            } catch (e: Exception) {
                updateState { it.copy(isLoading = false, errorMessage = "Erro ao carregar os CEPs salvos") }
            }
        }
    }

    fun onCepChanged(newCep: String) {
        updateState { it.copy(cepAtual = newCep, errorMessage = null) }
    }

    fun saveCep() {
        val cep = currentState().cepAtual
        if (!CepValidator.isValid(cep)) {
            updateState { it.copy(errorMessage = "CEP inválido. Digite 8 números.") }
            return
        }
        viewModelScope.launch {
            try {
                repository.saveCep(cep)
                updateState { it.copy(cepAtual = "", isSaved = true) }
                loadCeps() // recarrega a lista já com o novo CEP incluído
            } catch (e: Exception) {
                updateState { it.copy(errorMessage = "Erro ao salvar o CEP") }
            }
        }
    }

    fun onSavedMessageShown() { updateState { it.copy(isSaved = false) } }
    fun onErrorMessageShown() { updateState { it.copy(errorMessage = null) } }

    private fun currentState(): CepUiState = _uiState.value ?: CepUiState()
    private fun updateState(transform: (CepUiState) -> CepUiState) {
        _uiState.value = transform(currentState())
    }

    companion object {
        fun provideFactory(repository: CepRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return CepViewModel(repository) as T
                }
            }
    }
}