package com.example.findfriend.connectDB

import com.google.gson.annotations.SerializedName


data class Signup(
    var id: String,
    var password: String,
    var nickname: String
)

data class SignupResponse(
    val success: Boolean
)

data class Login(
    var id: String,
    var password: String
)

data class LoginResponse(
    val success: Boolean
)

data class addRoom(
    @SerializedName("room_name") val roomName: String?,
    @SerializedName("room_detail") val roomDetail: String?,
    @SerializedName("limtime") val limTime: Int,
    @SerializedName("location") val location: String?,
    @SerializedName("max_people") val maxPeople: Int,
    @SerializedName("min_people") val minPeople: Int,
    @SerializedName("owner") val owner: String?
)

data class addRoomResponse(
    val success: Boolean
)

data class joinRoom(
    @SerializedName("id") val myId: String?,
    @SerializedName("room_id") val roomId: Int?
)

data class joinRoomResponse(
    val success: Boolean
)
data class withdraw(
    @SerializedName("id") val myId: String?,
    @SerializedName("room_id") val roomId: Int?
)

data class withdrawResponse(
    val success: Boolean
)

data class delete(
    @SerializedName("id") val myId: String?,
    @SerializedName("room_id") val roomId: Int?
)

data class deleteResponse(
    val success: Boolean
)