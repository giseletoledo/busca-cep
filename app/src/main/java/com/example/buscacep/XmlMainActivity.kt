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
import com.example.buscacep.databinding.ActivityMain2Binding
import com.example.buscacep.domain.model.CepFormatter
import com.example.buscacep.livedata.CepUiStateLiveData
import com.example.buscacep.ui.viewmodel.CepViewModelLiveData

class XmlMainActivity : AppCompatActivity() {

    //ActivityMainBinding para ActivityMain2Binding
    private lateinit var binding: ActivityMain2Binding

    private val viewModel: CepViewModelLiveData by viewModels {
        val database = AppDatabase.getInstance(applicationContext)
        CepViewModelLiveData.provideFactory(CepRepositoryImpl(database.cepDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.buttonVoltar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeUiState() {
        viewModel.uiState.observe(this) { state -> render(state) }
    }

    private fun render(state: CepUiStateLiveData) {
        if (binding.editCep.text.toString() != state.cepAtual) {
            binding.editCep.setText(state.cepAtual)
            binding.editCep.setSelection(state.cepAtual.length)
        }

        binding.textCepsSalvos.text = if (state.ceps.isEmpty()) {
            "Nenhum CEP salvo ainda"
        } else {
            state.ceps.joinToString(separator = "\n") { cep ->
                CepFormatter.formatar(cep)
            }
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