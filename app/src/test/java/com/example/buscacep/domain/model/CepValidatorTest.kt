package com.example.buscacep.domain.model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
//testes unitários
class CepValidatorTest {

    @Test
    fun `isValid deve retornar true para um CEP valido com 8 digitos numericos`() {
        val cepValido = "01001000"
        assertTrue(CepValidator.isValid(cepValido))
    }

    @Test
    fun `isValid deve retornar false para um CEP com menos de 8 digitos`() {
        val cepCurto = "1234567"
        assertFalse(CepValidator.isValid(cepCurto))
    }

    @Test
    fun `isValid deve retornar false para um CEP com mais de 8 digitos`() {
        val cepLongo = "123456789"
        assertFalse(CepValidator.isValid(cepLongo))
    }

    @Test
    fun `isValid deve retornar false para um CEP que contem letras`() {
        val cepComLetras = "01001A00"
        assertFalse(CepValidator.isValid(cepComLetras))
    }

    @Test
    fun `isValid deve retornar false para um CEP vazio`() {
        val cepVazio = ""
        assertFalse(CepValidator.isValid(cepVazio))
    }

    @Test
    fun `isValid deve retornar false para um CEP com caracteres especiais`() {
        val cepComEspecial = "01001-00"
        assertFalse(CepValidator.isValid(cepComEspecial))
    }
}
