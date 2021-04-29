package com.example.tdd.data.remote.responses

import  com.example.tdd.data.remote.responses.ImageResult


data class ImageResponse(
    private var hits: List<ImageResult>,
    private  var total: Int,
    private  var totalHits: Int
)