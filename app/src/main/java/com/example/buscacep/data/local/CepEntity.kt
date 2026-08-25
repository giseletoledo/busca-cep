package com.example.buscacep.data.local

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "cep_table")
data class CepEntity(
    @PrimaryKey val id: Int = SINGLE_ROW_ID,
    val cep: String
) {
    companion object {
        const val SINGLE_ROW_ID = 1
    }
}

@Entity(tableName = "cep_table")
data class CepEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cep: String
)