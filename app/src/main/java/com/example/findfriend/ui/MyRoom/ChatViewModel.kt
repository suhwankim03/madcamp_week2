package com.example.findfriend.ui.MyRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatViewModel : ViewModel() {

    private val myChatList = mutableListOf<MyRoomDataModel?>()

    fun getMyRoomList(): MutableList<MyRoomDataModel?>{
        return myChatList
    }
    fun getMyRoom(position: Int): MyRoomDataModel?{
        return myChatList[position]
    }
    fun addMyRoom(myRoom: MyRoomDataModel?){
        myChatList.add(myRoom)
    }

    fun clearMyRoomList() {
        myChatList.clear()
    }


}