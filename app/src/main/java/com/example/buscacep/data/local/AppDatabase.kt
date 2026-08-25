package com.example.buscacep.data.local

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

@Database(entities = [CepEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cepDao(): CepDao

    companion object {
        private const val DATABASE_NAME = "busca_cep_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder<AppDatabase>(
                    context.applicationContext,
                    DATABASE_NAME
                )
                    .setDriver(BundledSQLiteDriver())
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}