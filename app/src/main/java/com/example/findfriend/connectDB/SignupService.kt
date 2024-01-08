package com.example.findfriend.connectDB
import com.example.findfriend.ui.FindRoom.FindRoomDataModel
import com.example.findfriend.ui.MyRoom.MyRoomDataModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

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

    @Headers("Content-Type: application/json")
    @GET("/get_nick")
    fun getNick(
        @Query("id") id: String
    ): Call<String>





}