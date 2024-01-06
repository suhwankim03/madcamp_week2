package com.example.findfriend.ui
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginService {


    @Headers("Content-Type: application/json")
    @POST("/signup")
    fun requestLogin(
        @Body login: Login
    ) : Call<LoginResponse>

}