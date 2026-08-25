package com.example.buscacep.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey
@Entity(tableName = "cep_table")
data class CepEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cep: String
)