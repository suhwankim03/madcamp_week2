package com.example.findfriend.ui.MyRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyRoomViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is my Room Fragment"
    }
    val text: LiveData<String> = _text
}