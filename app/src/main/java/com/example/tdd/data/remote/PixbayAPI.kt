package com.example.tdd.data.remote

import ImageResult
import com.example.tdd.BuildConfig
import com.example.tdd.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixbayAPI {

    @GET("/api/")
    suspend fun searchForImage(@Query("q") searchQuery: String,
    @Query("key") apiKey:String=BuildConfig.API_KEY
   ): Response<ImageResponse>

}