package com.example.tdd.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    @Query("Select * FROM shopping_item")
    fun observeAllShoppingItem():LiveData<List<ShoppingItem>>

    @Query("Select SUM(price * amount) FROM shopping_item")
    fun ObserverTotalPrice():LiveData<Float>
}