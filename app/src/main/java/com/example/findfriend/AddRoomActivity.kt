package com.example.findfriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.findfriend.databinding.ActivityAddRoomBinding
import com.example.findfriend.ui.RoomService
import com.example.findfriend.ui.SignupService
import com.example.findfriend.ui.addRoom
import com.example.findfriend.ui.addRoomResponse
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRoomBinding
    private lateinit var retrofit: Retrofit
    private lateinit var roomService:RoomService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofit = Retrofit.Builder()
            .baseUrl("http://143.248.199.213:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        roomService = retrofit.create(RoomService::class.java)

        val roomName = binding.writeRoomName
        val roomDetail = binding.writeRoomDetail
        val limitTime = binding.writeLimitTime
        val location= binding.writeLocation
        val maxNum= binding.writeMaxNumber
        val current_people:Int = 0
        val myID = GlobalApplication.prefs.getString("email","email 검색 오류")
        val completeButton =binding.completeButton

        completeButton.setOnClickListener {
            //작성 내용 null일 경우 오류나는 거 코드 추가 구현해줘야 함
            val newRoom = addRoom(roomName = roomName.toString(), roomDetail = roomDetail.toString(), limTime = limitTime.toString().toInt(), location = location.toString(), maxPeople = maxNum.toString().toInt(), minPeople = current_people, owner = myID)
                roomService.requestAddRoom(newRoom).enqueue(object: Callback<addRoomResponse> {
                override fun onResponse(
                    call: Call<addRoomResponse>,
                    response: Response<addRoomResponse>
                ) {
                    val successValue = response.body()
                    if (successValue != null) {
                        val issuc = successValue.success
                        if (issuc) {
                            Toast.makeText(this@AddRoomActivity, "방 생성!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@AddRoomActivity, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@AddRoomActivity, "방 생성 실패!", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                override fun onFailure(call: Call<addRoomResponse>, t: Throwable) {
                    Log.e("API Request", "Error: ${t.message}")
                }
            })
        }
    }
}