package com.example.buscacep

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.buscacep.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonIrXml.setOnClickListener {
            startActivity(Intent(this, XmlMainActivity::class.java))
        }

        binding.buttonIrCompose.setOnClickListener {
            startActivity(Intent(this, ComposeActivity::class.java))
        }
    }
}