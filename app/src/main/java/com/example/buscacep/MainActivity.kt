package com.example.buscacep

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.example.buscacep.data.local.AppDatabase
import com.example.buscacep.data.repository.CepRepositoryImpl
import com.example.buscacep.databinding.ActivityMainBinding
import com.example.buscacep.ui.CepUiState
import com.example.buscacep.ui.CepViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: CepViewModel by viewModels {
        val database = AppDatabase.getInstance(applicationContext)
        CepViewModel.provideFactory(CepRepositoryImpl(database.cepDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mantém o layout edge-to-edge
        enableEdgeToEdge()

        // Inicializa o View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mantém o ajuste automático para as barras do sistema
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        setupListeners()
        observeUiState()
    }

    private fun setupListeners() {
        binding.editCep.doAfterTextChanged { text ->
            viewModel.onCepChanged(text?.toString().orEmpty())
        }

        binding.buttonSalvar.setOnClickListener {
            viewModel.saveCep()
        }
    }

    private fun observeUiState() {
        viewModel.uiState.observe(this) { state -> render(state) }
    }

    private fun formatarCep(cep: String): String {
        return if (cep.length == 8) {
            "${cep.substring(0, 5)}-${cep.substring(5)}"
        } else {
            cep
        }
    }

    private fun render(state: CepUiState) {
        if (binding.editCep.text.toString() != state.cepAtual) {
            binding.editCep.setText(state.cepAtual)
            binding.editCep.setSelection(state.cepAtual.length)
        }

        binding.textCepsSalvos.text = if (state.ceps.isEmpty()) {
            "Nenhum CEP salvo ainda"
        } else {
            state.ceps.joinToString(separator = "\n") { cep -> formatarCep(cep) }
        }

        if (state.isSaved) {
            Toast.makeText(this, "CEP salvo!", Toast.LENGTH_SHORT).show()
            viewModel.onSavedMessageShown()
        }

        state.errorMessage?.let { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            viewModel.onErrorMessageShown()
        }
    }
}