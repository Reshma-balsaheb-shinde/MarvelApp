package com.example.marvelapp.Application

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.example.marvelapp.db.CharacterDatabase
import com.example.marvelapp.repository.CharacterRepository
import com.example.marvelapp.retrofit.RetrofitAPI
import com.example.marvelapp.retrofit.RetrofitHelper

@ExperimentalPagingApi
class MarvelApplication : Application() {

    lateinit var characterRepository: CharacterRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val retrofitAPI = RetrofitHelper.getInstance().create(RetrofitAPI::class.java)
        val database = CharacterDatabase.getDatabase(applicationContext)
        characterRepository = CharacterRepository(retrofitAPI, database, applicationContext)
    }
}