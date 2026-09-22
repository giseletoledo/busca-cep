package com.example.buscacep.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.buscacep.ui.CepColors
import com.example.buscacep.ui.viewmodel.CepViewModelFlow
import com.example.buscacep.ui.components.CepHeader
import com.example.buscacep.ui.components.CepInputCard
import com.example.buscacep.ui.components.CepSavedList

@Composable
fun CepScreen(viewModel: CepViewModelFlow) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            Toast.makeText(context, "CEP salvo!", Toast.LENGTH_SHORT).show()
            viewModel.onSavedMessageShown()
        }
    }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            viewModel.onErrorMessageShown()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CepColors.Paper)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        CepHeader()
        Spacer(Modifier.height(28.dp))
        CepInputCard(
            cep = state.cepAtual,
            onCepChange = viewModel::onCepChanged,
            onSalvarClick = viewModel::saveCep
        )
        Spacer(Modifier.height(32.dp))
        CepSavedList(ceps = state.ceps)
    }
}