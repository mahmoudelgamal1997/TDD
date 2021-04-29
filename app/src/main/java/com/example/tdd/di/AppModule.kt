package com.example.tdd.di

import android.content.Context
import androidx.room.Room
import com.example.tdd.data.local.ShoppingDao
import com.example.tdd.other.Constants.BASE_URL
import com.example.tdd.other.Constants.DATABASE_NAME
import com.example.tdd.data.local.ShoppingItemDataBase
import com.example.tdd.data.remote.PixbayAPI
import com.example.tdd.repositories.DefaultShoppingRepository
import com.example.tdd.repositories.ShoppingRepository
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
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDataBase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixbayAPI
    ) = DefaultShoppingRepository(dao, api) as ShoppingRepository

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDataBase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixbayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixbayAPI::class.java)
    }
}