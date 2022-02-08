package com.example.marvelapp.retrofit


import com.example.marvelapp.models.CharacterList
import com.example.marvelapp.models.Data
import com.example.marvelapp.models.Results
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

public interface RetrofitAPI {

    @GET("characters")
    suspend fun getCharactersFinal(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 20,
        @Query("orderBy") orderBy: String = "name"
    ): CharacterList


    @GET("characters")
    suspend fun searchCharacter(
        @Query("nameStartsWith") query: String,
        @Query("offset") offset: Int? = 0,
        @Query("limit") limit: Int? = 20
    ): CharacterList


    @GET("characters/{characterId}")
    suspend fun getCharacterDetail(
        @Path("characterId") characterId: Int
    ): CharacterList

}