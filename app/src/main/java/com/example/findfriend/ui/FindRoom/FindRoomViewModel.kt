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

    fun findPosition(roomId: Int, roomName: String,roomDetail: String, limTime: Int, location: String?, maxPeople: Int, minPeople: Int, owner: String): Int{
        var findRoom:FindRoomDataModel?
        for (i in 0 until findRoomList.size){
            findRoom = findRoomList[i]
            if ((findRoom?.roomId==roomId)&&(findRoom?.roomName==roomName)&&(findRoom.roomDetail==roomDetail)&&(findRoom.limTime==limTime)&&(findRoom.location==location)&&(findRoom.maxPeople==maxPeople)&&(findRoom.minPeople==minPeople)&&(findRoom.owner==owner)){
                return i
            }
        }
        return -1
    }
}