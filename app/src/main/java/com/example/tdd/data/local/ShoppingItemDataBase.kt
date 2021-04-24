package com.example.tdd.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tdd.data.local.ShoppingItem

@Database(
    entities = [ShoppingItem::class],
    version = 1
)

abstract class ShoppingItemDataBase: RoomDatabase() {
    abstract fun shoppingDao():ShoppingDao
}