package com.example.buscacep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.buscacep.data.local.AppDatabase
import com.example.buscacep.data.repository.CepRepositoryImpl
import com.example.buscacep.ui.CepScreen
import com.example.buscacep.ui.CepViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: CepViewModel by viewModels {
        viewModelFactory {
            initializer {
                val database = AppDatabase.getInstance(applicationContext)

                CepViewModel(
                    CepRepositoryImpl(database.cepDao())
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface {
                    CepScreen(viewModel)
                }
            }
        }
    }
}