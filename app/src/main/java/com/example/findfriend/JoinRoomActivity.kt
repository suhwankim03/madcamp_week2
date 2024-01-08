package com.example.findfriend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.findfriend.databinding.ActivityAddRoomBinding
import com.example.findfriend.databinding.ActivityJoinRoomBinding
import com.example.findfriend.ui.RoomService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JoinRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinRoomBinding
    private lateinit var retrofit: Retrofit
    private lateinit var roomService: RoomService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofit = Retrofit.Builder()
            .baseUrl("http://143.248.199.213:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        roomService = retrofit.create(RoomService::class.java)

        val roomName = binding.RoomName
        //val roomDetail = binding.RoomDetail
        val limitTime = binding.LimitTime
        val location= binding.Location
        val maxNum= binding.MaxNumber
        val owner= binding.owner
        val current_people = binding.currentNumber
        val myID = GlobalApplication.prefs.getString("email","email 검색 오류")
        val completeButton =binding.completeButton


        roomName.text = intent.getStringExtra("roomName")
        limitTime.text = intent.getStringExtra("limTime")
        location.text = intent.getStringExtra("location")
        //roomDetail.text = intent.getLongExtra("roomName",-1).toString()
        current_people.text = intent.getStringExtra("currentPeople")
        maxNum.text = intent.getStringExtra("maxPeople")
        owner.text = intent.getStringExtra("owner")



    }
}