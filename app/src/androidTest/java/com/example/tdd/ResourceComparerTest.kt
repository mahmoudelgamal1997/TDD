package com.example.tdd

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ResourceComparerTest{
    lateinit var resourceComparer: ResourceComparer

    @Before
    fun setUp(){
        resourceComparer = ResourceComparer()
    }

    @Test
    fun Compare_StringResourece_as_Given_return_true(){
        val context= ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context,R.string.app_name,"TDD")
        assertThat(result).isTrue()
    }

}