package com.example.tdd.di

import android.content.Context
import androidx.room.Room
import com.example.tdd.other.Constants.BASE_URL
import com.example.tdd.other.Constants.DATABASE_NAME
import com.example.tdd.data.local.ShoppingItemDataBase
import com.example.tdd.data.remote.PixbayAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDataBase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context,ShoppingItemDataBase::class.java,DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingDao(
        dataBase: ShoppingItemDataBase
    )=dataBase.shoppingDao()


    @Singleton
    @Provides
    fun providePixAPI():PixbayAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixbayAPI::class.java)
    }
}