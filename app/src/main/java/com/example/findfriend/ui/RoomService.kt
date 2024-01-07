package com.example.findfriend.ui
import com.example.findfriend.ui.FindRoom.FindRoomDataModel
import com.example.findfriend.ui.MyRoom.MyRoomDataModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface RoomService {
    @Headers("Content-Type: application/json")
    @GET("/get_room")
    fun getRoom() : Call<List<MyRoomDataModel>>

    @Headers("Content-Type: application/json")
    @GET("/get_myroom")
    fun getRoom2(
        @Query("id") id: String
    ): Call<List<FindRoomDataModel>>

}