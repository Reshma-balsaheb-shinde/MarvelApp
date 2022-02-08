package com.example.marvelapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.example.marvelapp.common.CharacterDataSource
import com.example.marvelapp.common.LocalInjector
import com.example.marvelapp.common.RemoteInjector
import com.example.marvelapp.db.CharacterDatabase
import com.example.marvelapp.models.Results
import com.example.marvelapp.retrofit.RetrofitAPI

import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class CharacterRepository(
    private val retrofitAPI: RetrofitAPI = RemoteInjector.injectDoggoApiService(),
    private val characterDatabase: CharacterDatabase? = LocalInjector.injectDb()
) {
    private val CharacterLiveData = MutableLiveData<Results>()

    val characterDescription: LiveData<Results>
        get() = CharacterLiveData

    val resultsDao = characterDatabase!!.getResults()

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

        fun getInstance() = CharacterRepository()
    }

    fun searchCharacter(query: String) = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100, enablePlaceholders = false),
        pagingSourceFactory = { CharacterDataSource(retrofitAPI, query) }
    ).flow

    /**
     * calling the paging source to give results from api calls
     * and returning the results in the form of flow [Flow<PagingData<DoggoImageModel>>]
     * since the [PagingDataAdapter] accepts the [PagingData] as the source in later stage
     */
    fun letCharacterDataFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<Results>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { CharacterDataSource(retrofitAPI,"") }
        ).flow
    }

    suspend fun getCharacterDetails(charId: Int) {
        val result = retrofitAPI.getCharacterDetail(charId)
        CharacterLiveData.postValue(result.data!!.results.get(0))
    }

    /**
     * let's define page size, page size is the only required param, rest is optional
     */
    fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }


    suspend fun addCharacterToFavourite(characterResult: Results) =
        resultsDao.insert(characterResult)

    suspend fun removeCharacterFromFavourite(characterResult: Results) =
        resultsDao.delete(characterResult)

    fun getFavouriteCharacters() =
        resultsDao.getFavouriteCharacters()



}