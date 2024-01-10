package com.example.findfriend.ui.MyRoom

import com.google.gson.annotations.SerializedName
import java.sql.Date


data class RoomChatDataModel(
    @SerializedName("message") val message: String?
){
    constructor() : this(null)
}
