package com.example.buscacep.domain.model

object CepValidator {
    private const val CEP_LENGTH = 8

    fun isValid(cep: String): Boolean {
        val temOitoDigitos = cep.length == CEP_LENGTH
        val contemApenasNumeros = cep.all { caractere -> caractere.isDigit() }
        return temOitoDigitos && contemApenasNumeros
    }
}