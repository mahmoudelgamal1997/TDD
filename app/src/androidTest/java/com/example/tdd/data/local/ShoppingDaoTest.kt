package com.example.tdd.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.google.common.truth.Truth.assertWithMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
@SmallTest
class ShoppingDaoTest {

    lateinit var database:ShoppingItemDataBase
    lateinit var  dao:ShoppingDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Before
    fun setUp(){

        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), ShoppingItemDataBase::class.java)
                .allowMainThreadQueries()
                .build()
        dao =database.shoppingDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest{
        var shoppingitem=ShoppingItem("car", 120F, 10, "sss", 1)
        dao.insertShoppingItem(shoppingitem)

        val allShoppingItems = dao.observeAllShoppingItem().getOrAwaitValue()
        assertThat(allShoppingItems).contains(shoppingitem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {

        var shoppingItem=ShoppingItem("car",120F,10,"sss",1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItem = dao.observeAllShoppingItem().getOrAwaitValue()
        assertThat(allShoppingItem).doesNotContain(shoppingItem)
    }

}