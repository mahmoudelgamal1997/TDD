package com.example.tdd

object RegisterationUtils {

    val users= listOf("medo","hamdy")
    fun validateRegisterationInput(
        username:String,
        password:String,
        confirmpassword:String
    ):Boolean{
        if (username.isEmpty() || password.isEmpty() ){
            return false;
        }
        if (username in users){
            return false
        }
        if (password != confirmpassword){
            return false
        }
        if (password.length<2 ){
            return false
        }
        return  true
    }
}