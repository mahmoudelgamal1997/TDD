package com.example.tdd.data.remote.responses

import ImageResult

data class ImageResponse (
    val hits:List<ImageResult>,
    val total:Int,
    val totalHints:Int
        )