package com.example.marvelapp.Application

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.example.marvelapp.common.LocalInjector
import com.example.marvelapp.db.CharacterDatabase
import com.example.marvelapp.repository.CharacterRepository
import com.example.marvelapp.retrofit.RetrofitAPI
import com.example.marvelapp.retrofit.RetrofitHelper
import org.w3c.dom.CharacterData

@ExperimentalPagingApi
class MarvelApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LocalInjector.appDatabase = CharacterDatabase.getInstance(this@MarvelApplication)

    }
/*
    lateinit var characterRepository: CharacterRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val quoteService = RetrofitHelper.getInstance().create(RetrofitAPI::class.java)
        val database = CharacterDatabase.getDatabase(applicationContext)
        characterRepository = CharacterRepository(quoteService, database, applicationContext)
    }*/
}