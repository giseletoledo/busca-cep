package com.example.buscacep.data.local

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query

@Dao
interface CepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCep(cepEntity: CepEntity)

    @Query("SELECT * FROM cep_table WHERE id = :id")
    suspend fun getCep(id: Int = CepEntity.SINGLE_ROW_ID): CepEntity?
}