package com.example.tdd.repositories

import androidx.lifecycle.LiveData
import com.example.tdd.data.local.ShoppingDao
import com.example.tdd.data.local.ShoppingItem
import com.example.tdd.data.remote.PixbayAPI
import com.example.tdd.data.remote.responses.ImageResponse
import com.example.tdd.other.Resource
import java.lang.Exception
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
  private  val dao:ShoppingDao,
  private  val pixbayAPI: PixbayAPI
        ):ShoppingRepository{

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        dao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        dao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllItems(): LiveData<List<ShoppingItem>> {
       return dao.observeAllShoppingItem()
     }

    override fun totalPrice(): LiveData<Float> {
            return dao.ObserverTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
          return  try {
             val response = pixbayAPI.searchForImage(imageQuery)
                if (response.isSuccessful) {
                response.body()?.let {
                    Resource.success(it)
                }?: Resource.error("an error has occured",null)

                }else {
                    Resource.error("an error has Occured",null)
                }
          }catch (e:Exception){
              Resource.error("an error has occured ",null)
          }
    }
}