package com.example.marvelapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.marvelapp.models.Results

@Database(entities = [Results::class], version = 1)
abstract class CharacterDatabase : RoomDatabase() {

    abstract fun getResults() : ResultsDao

    companion object {
        val Character_DB = "characterDB.db"

        @Volatile
        private var INSTANCE: CharacterDatabase? = null

        fun getDatabase(context: Context): CharacterDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        CharacterDatabase::class.java,
                        Character_DB
                    )
                        .build()
                }
            }
            return INSTANCE!!

        }
    }
}