package com.example.tdd.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_item")
data class ShoppingItem(
    var name:String,
    var price:Float,
    var amount:Int,
    var imageURL:String,
    @PrimaryKey
    val id:Int? = null
)