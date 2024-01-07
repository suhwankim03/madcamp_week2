package com.example.findfriend.ui
import com.example.findfriend.ui.MyRoom.MyRoomDataModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface RoomService {
    @Headers("Content-Type: application/json")
    @GET("/get_room")
    fun getRoom() : Call<MyRoomDataModel>

}