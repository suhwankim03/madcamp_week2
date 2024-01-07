package com.example.findfriend.ui.MyRoom

import java.sql.Date

data class MyRoomDataModel(
    val roomID: Int,
    val roomName: String,
    val roomDetail: String,
    val limTime: Int,
    val location: String,
    val maxPeople: Int,
    val currentPeople: Int,
    val ownerName: String)
