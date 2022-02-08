package com.example.marvelapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.marvelapp.models.Data
import com.example.marvelapp.models.Results

@Database(entities = [Results::class], version = 1)
abstract class CharacterDatabase : RoomDatabase() {

    abstract fun getResults() : ResultsDao

    companion object {

        val DOGGO_DB = "characterDB.db"

        @Volatile
        private var INSTANCE: CharacterDatabase? = null

        fun getInstance(context: Context): CharacterDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, CharacterDatabase::class.java, DOGGO_DB)
                .build()
    }

}