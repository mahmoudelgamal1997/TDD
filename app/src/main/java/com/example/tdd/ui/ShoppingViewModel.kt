package com.example.tdd.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tdd.data.local.ShoppingItem
import com.example.tdd.data.remote.responses.ImageResponse
import com.example.tdd.other.Constants
import com.example.tdd.other.Event
import com.example.tdd.other.Resource
import com.example.tdd.other.Status
import com.example.tdd.repositories.ShoppingRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class ShoppingViewModel @ViewModelInject constructor(private  val repository: ShoppingRepository):ViewModel(){

    val shoppingItems = repository.observeAllItems()

    val totalPrice = repository.totalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl: LiveData<String> = _curImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItemStatus

    fun setCurImageUrl(url: String) {
        _curImageUrl.postValue(url)
    }
    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }
    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }
    fun insertShoppingItem(name: String, amountString: String, priceString: String) {
            if (name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()){
                _insertShoppingItemStatus.postValue(Event(Resource.error("the field must not be emputy",null)))
                return
            }
            if (name.length>Constants.MAX_NAME_LENGHT){
                _insertShoppingItemStatus.postValue(Event(Resource.error("name must be less than ${Constants.MAX_NAME_LENGHT}"
                    ,null)))
            return
            }

        if (priceString.length>Constants.MAX_PRICE_LENGHT){
            _insertShoppingItemStatus.postValue(Event(Resource.error("price  must be less than ${Constants.MAX_PRICE_LENGHT}"
                ,null)))
            return
        }
        val amount = try {
            amountString.toInt()
        }catch (ex:Exception){
            _insertShoppingItemStatus.postValue(Event(Resource.error("enter a valid number",null)))
            return
        }

        val shoppingItem=ShoppingItem(name,priceString.toFloat(),amount,_curImageUrl.value ?:"")
        insertShoppingItemIntoDb(shoppingItem)
        setCurImageUrl("")
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
    }
    fun searchForImage(imageQuery: String) {

        if (imageQuery.isEmpty()){
            return
        }
        _images.postValue(Event(Resource.loading(null)))

        viewModelScope.launch {
            val response = repository.searchForImage(imageQuery)
            _images.value = Event(response)
        }
    }
}