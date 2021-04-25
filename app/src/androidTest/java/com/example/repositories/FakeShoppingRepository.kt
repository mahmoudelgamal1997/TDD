package com.example.repositories

import android.media.Image
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tdd.data.local.ShoppingItem
import com.example.tdd.data.remote.responses.ImageResponse
import com.example.tdd.other.Resource
import com.example.tdd.repositories.ShoppingRepository

class FakeShoppingRepository():ShoppingRepository {


    private val shoppingItems= mutableListOf<ShoppingItem>()
    private val observeShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observeTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetWorkError = false

    fun setShouldReturnNetworkError (value:Boolean){
        shouldReturnNetWorkError=value
    }

    fun refreshlivedata(){
        observeShoppingItems.postValue(shoppingItems)
        observeTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice():Float{
        return shoppingItems.sumByDouble { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
            shoppingItems.add(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
    }

    override fun observeAllItems(): LiveData<List<ShoppingItem>> {
        return observeShoppingItems
    }

    override fun totalPrice(): LiveData<Float> {
        return observeTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return    if (shouldReturnNetWorkError){
            Resource.error("error",null)
            }else {
                Resource.success(ImageResponse(listOf(),0,0))
        }

    }

}