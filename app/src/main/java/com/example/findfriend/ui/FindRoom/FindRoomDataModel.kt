package com.example.findfriend.ui.FindRoom

import com.google.gson.annotations.SerializedName

data class FindRoomDataModel(
    @SerializedName("room_id") val roomId: Int,
    @SerializedName("room_name") val roomName: String?,
    @SerializedName("room_detail") val roomDetail: String?,
    @SerializedName("limtime") val limTime: Int,
    @SerializedName("location") val location: String?,
    @SerializedName("max_people") val maxPeople: Int,
    @SerializedName("min_people") val minPeople: Int,
    @SerializedName("owner") val ownerID: String?,
    @SerializedName("owner_nick") val ownerNickname: String?

){
    constructor() : this(0, null, null, 0, null, 0, 0, null,null)
}

