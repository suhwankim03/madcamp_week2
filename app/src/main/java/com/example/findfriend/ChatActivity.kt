package com.example.findfriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.findfriend.databinding.ActivityChatBinding
import com.example.findfriend.ui.RoomService
import com.example.findfriend.ui.joinRoom
import com.example.findfriend.ui.joinRoomResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var retrofit: Retrofit
    private lateinit var roomService: RoomService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofit = Retrofit.Builder()
            .baseUrl("http://143.248.199.213:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        roomService = retrofit.create(RoomService::class.java)

        val roomName = binding.RoomName
        val roomID = binding.roomID
        //val roomDetail = binding.RoomDetail
        val limitTime = binding.LimitTime
        val location= binding.Location
        val maxNum= binding.MaxNumber
        val owner= binding.owner
        val current_people = binding.currentNumber
        val myID = GlobalApplication.prefs.getString("email","email 검색 오류")
        val completeButton =binding.completeButton


        roomID.text = intent.getStringExtra("roomID")
        roomName.text = intent.getStringExtra("roomName")
        limitTime.text = intent.getStringExtra("limTime")
        location.text = intent.getStringExtra("location")
        //roomDetail.text = intent.getLongExtra("roomName",-1).toString()
        current_people.text = intent.getStringExtra("currentPeople")
        maxNum.text = intent.getStringExtra("maxPeople")
        owner.text = intent.getStringExtra("owner")

        val textRoomID = (roomID.text as String).toInt()

        //누른 카드뷰의 데이터 받아오는 것 까지 구현 완료 나머지 추가 작업은 나중에!!


        /*completeButton.setOnClickListener {
            //작성 내용 null일 경우 오류나는 거 코드 추가 구현해줘야 함
            val newRoom = joinRoom(roomId = textRoomID, myId= myID)
            roomService.requestJoinRoom(newRoom).enqueue(object: Callback<joinRoomResponse> {
                override fun onResponse(
                    call: Call<joinRoomResponse>,
                    response: Response<joinRoomResponse>
                ) {
                    val successValue = response.body()
                    if (successValue != null) {
                        val issuc = successValue.success
                        if (issuc) {
                            Toast.makeText(this@ChatActivity, "방에 합류!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@ChatActivity, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@ChatActivity, "방에 못들어감!", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                override fun onFailure(call: Call<joinRoomResponse>, t: Throwable) {
                    Log.e("API Request", "Error: ${t.message}")
                }
            })
        }*/
    }
}