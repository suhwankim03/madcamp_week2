package com.example.findfriend.ui.FindRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FindRoomViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is findroom Fragment"
    }
    val text: LiveData<String> = _text
}