package com.example.buscacep.data.local

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query

@Dao
interface CepDao {

    @Insert
    suspend fun insertCep(cepEntity: CepEntity)

    @Query("SELECT * FROM cep_table ORDER BY id DESC")
    suspend fun getAllCeps(): List<CepEntity>
}