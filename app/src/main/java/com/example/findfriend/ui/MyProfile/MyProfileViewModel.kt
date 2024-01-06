package com.example.findfriend.ui.MyProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is my profile Fragment"
    }
    val text: LiveData<String> = _text
}