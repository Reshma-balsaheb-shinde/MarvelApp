package com.example.marvelapp.viewModel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marvelapp.db.CharacterDatabase
import com.example.marvelapp.db.ResultsDao
import com.example.marvelapp.getOrAwaitValue
import com.example.marvelapp.models.Results
import com.example.marvelapp.models.Thumbnail
import com.example.marvelapp.repository.CharacterRepository
import com.example.marvelapp.retrofit.RetrofitAPI
import com.example.marvelapp.retrofit.RetrofitHelper
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class MainViewModelTest :  TestCase() {

    private lateinit var viewModel: MainViewModel
    private lateinit var db: CharacterDatabase
    private lateinit var retrofitAPI: RetrofitAPI
    private lateinit var dao: ResultsDao
    val ResultList = Results(1,1,"Spiderman",
        "spinderman","10/02/2022", Thumbnail("https://media.istockphoto.com/pho",".jpg" )
    )
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Override function setUp() and annotate it with @Before
    // this function will be called at first when this test class is called
    @Before
    public override fun setUp() {
        // get context -- since this is an instrumental test it requires
        // context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()
        // initialize the db and dao variable
        retrofitAPI = RetrofitHelper.getInstance().create(RetrofitAPI::class.java)
        db = Room.inMemoryDatabaseBuilder(context, CharacterDatabase::class.java).allowMainThreadQueries().build()
        dao = db.getResults()
        val repository = CharacterRepository(retrofitAPI, db, context)
        viewModel = MainViewModel(repository)
    }

    // Override function closeDb() and annotate it with @After
    // this function will be called at last when this test class is called
    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testInsertandGetFunc() {

        viewModel.addToFavourite(ResultList)
        val results = viewModel.getFavouriteCharacters().getOrAwaitValue().find {

                it.id == 1 && it.name == "Spiderman"&& it.description == "spinderman"

        }
        assertThat(results != null).isTrue()
    }
    @Test
    fun testInsertandDeleteFunc() {

        viewModel.addToFavourite(ResultList)
        viewModel.removeFromFavourite(ResultList)
        val resultDelete = viewModel.getFavouriteCharacters().getOrAwaitValue()
        assertThat(resultDelete.contains(ResultList)).isFalse()
    }

    @Test
    fun getCharactersFromAPI()
    {
        val data = viewModel.fetchCharacterData()
        assertThat(data)

    }
}