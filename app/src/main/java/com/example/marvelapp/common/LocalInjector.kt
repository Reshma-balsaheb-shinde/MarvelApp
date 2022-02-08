package com.example.marvelapp.common

import com.example.marvelapp.db.CharacterDatabase


object LocalInjector {

    var appDatabase: CharacterDatabase? = null

    fun injectDb(): CharacterDatabase? {
        return appDatabase
    }
}