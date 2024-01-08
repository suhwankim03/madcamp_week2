package com.example.findfriend.connectDB
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignupService {


    @Headers("Content-Type: application/json")
    @POST("/signup")
    fun requestSignup(
        @Body signup: Signup
    ) : Call<SignupResponse>

    @POST("/login")
    fun requestLogin(
        @Body login: Login
    ) : Call<LoginResponse>




}