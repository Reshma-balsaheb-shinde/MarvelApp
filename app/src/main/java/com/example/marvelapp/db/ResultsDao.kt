package com.example.marvelapp.db

import androidx.paging.PagingSource
import androidx.room.*
import com.example.marvelapp.models.Results
import kotlinx.coroutines.flow.Flow

@Dao
interface ResultsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Results)

    @Delete
    suspend fun delete(character: Results)

    @Query("SELECT * FROM Results")
    fun getFavouriteCharacters(): Flow<List<Results>>


}