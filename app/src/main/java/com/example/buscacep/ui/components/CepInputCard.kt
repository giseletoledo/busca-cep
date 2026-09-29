package com.example.buscacep.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.buscacep.domain.model.CepEndereco

@Composable
fun CepInputCard(
    cep: String,
    onCepChange: (String) -> Unit,
    onSalvarClick: () -> Unit,
    endereco: CepEndereco? = null,
    isLoadingEndereco: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = cep,
                onValueChange = onCepChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("CEP") },
                singleLine = true
            )

            if (isLoadingEndereco) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    LoadingDots()
                }
            } else if (endereco != null) {
                Column(Modifier.padding(top = 12.dp)) {
                    Text(endereco.logradouro, style = MaterialTheme.typography.bodyMedium)
                    Text(endereco.bairro, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        "${endereco.cidade} / ${endereco.uf}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Button(
                onClick = onSalvarClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text("Salvar")
            }
        }
    }
}