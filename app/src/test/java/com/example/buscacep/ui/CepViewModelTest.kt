package com.example.buscacep.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.buscacep.domain.repository.CepRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CepViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private val repository = mockk<CepRepository>(relaxed = true)
    private lateinit var viewModel: CepViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CepViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `saveCep deve mostrar erro se o CEP for invalido`() {
        // Given
        viewModel.onCepChanged("123") // Inválido (menos de 8 dígitos)

        // When
        viewModel.saveCep()

        // Then
        assertEquals("CEP inválido. Digite 8 números.", viewModel.uiState.value?.errorMessage)
        coVerify(exactly = 0) { repository.saveCep(any()) }
    }

    @Test
    fun `saveCep deve chamar o repositorio se o CEP for valido`() {
        // Given
        val cepValido = "01001000"
        viewModel.onCepChanged(cepValido)

        // When
        viewModel.saveCep()

        // Then
        coVerify(exactly = 1) { repository.saveCep(cepValido) }
        assertNull(viewModel.uiState.value?.errorMessage)
    }

    @Test
    fun `loadCeps deve atualizar a lista de CEPs do estado`() {
        // Given
        val listaEsperada = listOf("01001000", "20040000")
        coEvery { repository.getAllCeps() } returns listaEsperada

        // When
        viewModel.loadCeps()

        // Then
        assertEquals(listaEsperada, viewModel.uiState.value?.ceps)
    }
}
