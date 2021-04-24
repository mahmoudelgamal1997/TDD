package com.example.tdd

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class RegisterationUtilsTest{

@Test
fun `emputy username return false `(){
        val result = RegisterationUtils.validateRegisterationInput("",
        "123",
            "123"
            )

    assertThat(result).isFalse()
}

    @Test
    fun `valid username and password equal confirm password `(){
        val result = RegisterationUtils.validateRegisterationInput("gemy",
            "123",
            "123"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `username already exist return false`(){
        val result = RegisterationUtils.validateRegisterationInput("medo",
            "123",
            "123"
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `emputy password`(){
        val result = RegisterationUtils.validateRegisterationInput("medo",
            "",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `incorrectly confirmed password`(){
        val result = RegisterationUtils.validateRegisterationInput("medo",
            "123123",
            "mmmmmm"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `password less than 2 digits `(){
        val result = RegisterationUtils.validateRegisterationInput("medo",
            "1",
            "1"
        )
        assertThat(result).isFalse()
    }

}