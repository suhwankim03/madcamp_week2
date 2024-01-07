package com.example.findfriend.ui.MyRoom

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class MyRoomDataModel(
    @SerializedName("room_id") val roomId: Int,
    @SerializedName("room_name") val roomName: String,
    @SerializedName("room_detail") val roomDetail: String,
    @SerializedName("limtime") val limTime: Int,
    @SerializedName("location") val location: String,
    @SerializedName("max_people") val maxPeople: Int,
    @SerializedName("min_people") val minPeople: Int,
    @SerializedName("owner") val owner: String
)
