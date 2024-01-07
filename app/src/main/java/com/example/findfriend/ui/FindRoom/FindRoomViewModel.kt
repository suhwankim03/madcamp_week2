package com.example.findfriend.ui.FindRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.findfriend.ui.MyRoom.MyRoomDataModel

class FindRoomViewModel : ViewModel() {

    private val findRoomList = mutableListOf<FindRoomDataModel?>()

    fun getFindRoomList(): MutableList<FindRoomDataModel?>{
        return findRoomList
    }
    fun getMyRoom(position: Int): FindRoomDataModel?{
        return findRoomList[position]
    }
    fun addMyRoom(findRoom: FindRoomDataModel?){
        findRoomList.add(findRoom)
    }
}