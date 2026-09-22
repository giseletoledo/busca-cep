package com.example.buscacep.domain.model

object CepFormatter {
    fun formatar(cep: String): String {
        return if (cep.length == 8) {
            "${cep.substring(0, 5)}-${cep.substring(5)}"
        } else {
            cep
        }
    }
}