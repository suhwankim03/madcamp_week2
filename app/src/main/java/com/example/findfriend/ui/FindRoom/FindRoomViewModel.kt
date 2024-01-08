package com.example.findfriend.ui.FindRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.findfriend.ui.MyRoom.MyRoomDataModel
import com.google.gson.annotations.SerializedName

class FindRoomViewModel : ViewModel() {

    private val findRoomList = mutableListOf<FindRoomDataModel?>()

    fun getFindRoomList(): MutableList<FindRoomDataModel?>{
        return findRoomList
    }
    fun clickRoom(position: Int): FindRoomDataModel?{
        return findRoomList[position]
    }
    fun addMyRoom(findRoom: FindRoomDataModel?){
        findRoomList.add(findRoom)
    }

    fun clearFindRoomList() {
        findRoomList.clear()
    }

}