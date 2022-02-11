package com.example.marvelapp.db

import android.content.ClipData
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marvelapp.models.Results
import com.example.marvelapp.models.Thumbnail
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import org.junit.Assert
import java.io.IOException
import android.content.ClipData.Item




@RunWith(AndroidJUnit4::class)
class CharacterDatabaseTest :  TestCase() {
    // get reference to the LanguageDatabase and LanguageDao class
    private lateinit var db: CharacterDatabase
    private lateinit var dao: ResultsDao
    val ResultList = Results(1,1,"Spiderman",
        "spinderman","10/02/2022", Thumbnail("https://media.istockphoto.com/pho",".jpg" )
    )
    // Override function setUp() and annotate it with @Before
    // this function will be called at first when this test class is called
    @Before
    public override fun setUp() {
        // get context -- since this is an instrumental test it requires
        // context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()
        // initialize the db and dao variable
        db = Room.inMemoryDatabaseBuilder(context, CharacterDatabase::class.java).build()
        dao = db.getResults()
    }

    // Override function closeDb() and annotate it with @After
    // this function will be called at last when this test class is called
    @After
    fun closeDb() {
        db.close()
    }

    // create a test function and annotate it with @Test
    // here we are first adding an item to the db and then checking if that item
    // is present in the db -- if the item is present then our test cases pass
    @Test
    fun testInsertandGetFunc() = runBlocking {
        dao.insert(ResultList)
        val results  = dao.getFavouriteCharacters().first()
        assertThat(results.contains(ResultList)).isTrue()
        
    }

    @Test
    fun testInsertandDeleteFunc() = runBlocking{
        dao.insert(ResultList)
        val results  = dao.getFavouriteCharacters().first()
        dao.delete(ResultList)
        val resultsDelete  = dao.getFavouriteCharacters().first()

        assertThat(resultsDelete.contains(ResultList)).isFalse()
    }
}