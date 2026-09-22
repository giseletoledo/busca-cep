package com.example.buscacep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.buscacep.data.local.AppDatabase
import com.example.buscacep.data.repository.CepRepositoryImpl
import com.example.buscacep.ui.screens.CepScreen
import com.example.buscacep.ui.viewmodel.CepViewModelFlow

class ComposeActivity : ComponentActivity() {

    private val viewModel: CepViewModelFlow by viewModels {
        viewModelFactory {
            initializer {
                val database = AppDatabase.getInstance(applicationContext)
                CepViewModelFlow(
                    CepRepositoryImpl(database.cepDao())
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()   // <- novo

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CepScreen(viewModel)
                }
            }
        }
    }
}