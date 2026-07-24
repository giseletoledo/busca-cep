package com.example.buscacep

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buscacep.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences

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

        // SharedPreferences
        preferences = getSharedPreferences("cep_preferences", MODE_PRIVATE)

        // Restaura o CEP salvo
        val cepSalvo = preferences.getString("cep", "")
        binding.editCep.setText(cepSalvo)

        // Salva ao clicar no botão
        binding.buttonSalvar.setOnClickListener {

            val cep = binding.editCep.text.toString()

            preferences.edit()
                .putString("cep", cep)
                .apply()
            Toast.makeText(this, "CEP salvo!", Toast.LENGTH_SHORT).show()

            val cepLido = preferences.getString("cep", "")

            Log.d("SharedPreferences", "Valor lido: $cepLido")
        }
    }
}