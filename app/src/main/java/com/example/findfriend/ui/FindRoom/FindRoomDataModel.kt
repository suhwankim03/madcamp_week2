package com.example.findfriend.ui.FindRoom

data class FindRoomDataModel(
    val roomID: Int,
    val roomName: String,
    val roomDetail: String,
    val limTime: Int,
    val location: String,
    val maxPeople: Int,
    val currentPeople: Int,
    val ownerName: String)
