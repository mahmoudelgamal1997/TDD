package com.example.tdd.repositories

import androidx.lifecycle.LiveData
import com.example.tdd.data.local.ShoppingItem
import com.example.tdd.data.remote.responses.ImageResponse
import com.example.tdd.other.Resource
import retrofit2.Response

interface ShoppingRepository  {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

     fun observeAllItems():LiveData<List<ShoppingItem>>

     fun totalPrice():LiveData<Float>

     suspend fun searchForImage(imageQuery:String):Resource<ImageResponse>
}