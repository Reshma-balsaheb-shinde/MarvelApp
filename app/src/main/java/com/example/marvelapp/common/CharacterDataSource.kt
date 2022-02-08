package com.example.marvelapp.common

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import com.example.marvelapp.models.Results
import com.example.marvelapp.repository.CharacterRepository.Companion.DEFAULT_PAGE_INDEX
import com.example.marvelapp.retrofit.RetrofitAPI
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class CharacterDataSource(private val retrofitAPI: RetrofitAPI, private val query: String) : PagingSource<Int, Results>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Results> {
        //for first case it will be null, then we can pass some default value, in our case it's 1
        val page = params.key ?: DEFAULT_PAGE_INDEX


        return try {
            val response_final  = if (query != "") {
                val response = retrofitAPI.searchCharacter(query = query, offset = page, limit = params.loadSize)
                val data = response.data
                data!!.results
            } else {
                val response = retrofitAPI.getCharactersFinal(offset = page, limit = params.loadSize)
                val data = response.data
                data!!.results
            }

            LoadResult.Page(
                response_final, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (response_final.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            Log.e("TAG","exception==$exception.toString()")
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e("TAG","exception==$exception.toString()")
            return LoadResult.Error(exception)
        }
    }
}