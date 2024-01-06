package com.example.findfriend.ui.CreateAccount
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {


    @Headers("Content-Type: application/json")
    @POST("/sendserver")
    fun requestLogin(
        @Body login: Login
    ) : Call<Login>

}