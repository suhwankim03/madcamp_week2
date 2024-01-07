package com.example.findfriend.ui.MyRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyRoomViewModel : ViewModel() {

    private val myRoomList = mutableListOf<MyRoomDataModel>()

    fun getMyRoomList(): MutableList<MyRoomDataModel>{
        return myRoomList
    }
    fun getMyRoom(position: Int): MyRoomDataModel{
        return myRoomList[position]
    }
    fun addMyRoom(myRoom: MyRoomDataModel){
        myRoomList.add(myRoom)
    }
}