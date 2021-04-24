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

    @Test
    fun observeTotalPrice() = runBlockingTest {
        var shoppingItem1 = ShoppingItem("car",120F,10,"sss",1)
        var shoppingItem2 = ShoppingItem("car",10F,20,"sss",2)
        var shoppingItem3 = ShoppingItem("car",12F,30,"sss",3)

        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val totalPrice = dao.ObserverTotalPrice().getOrAwaitValue()
        assertThat(totalPrice).isEqualTo(120F*10+ 10F*20 +12F*30)

    }
}