package com.example.findfriend.ui.FindRoom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.findfriend.Settings.GlobalApplication
import com.example.findfriend.Settings.GlobalApplication.Companion.roomService
import com.example.findfriend.databinding.ActivityJoinRoomBinding
import com.example.findfriend.connectDB.RoomService
import com.example.findfriend.connectDB.joinRoom
import com.example.findfriend.connectDB.joinRoomResponse
import com.example.findfriend.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class JoinRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinRoomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val roomName = binding.RoomName
        val roomDetail = binding.RoomDetail
        val limitTime = binding.LimitTime
        val location= binding.Location
        val maxNum= binding.MaxNumber
        val ownerNickname= binding.ownerNickname
        val current_people = binding.currentNumber
        val myID = GlobalApplication.prefs.getString("email","email 검색 오류")
        val completeButton =binding.completeButton
        val goBackButton =binding.backButton

        roomName.text = intent.getStringExtra("roomName")
        limitTime.text = intent.getStringExtra("limTime")
        location.text = intent.getStringExtra("location")
        roomDetail.text = intent.getStringExtra("roomDetail")
        current_people.text = intent.getStringExtra("currentPeople")+" / "
        val current_num = intent.getStringExtra("currentPeople")?.toInt()
        maxNum.text = intent.getStringExtra("maxPeople")
        ownerNickname.text = intent.getStringExtra("owner_nick")

        val textRoomID = (intent.getStringExtra("roomID") as String).toInt()

        goBackButton.setOnClickListener {
            val intent = Intent(this@JoinRoomActivity, MainActivity::class.java)
            startActivity(intent)
            //FindRoomFragment로 이동할 수 있게 코드 추가하면 좋을듯
        }

        completeButton.setOnClickListener {
            //작성 내용 null일 경우 오류나는 거 코드 추가 구현해줘야 함
            if (current_num != null) {
                if (current_num >= maxNum.text.toString().toInt()){

                    Toast.makeText(this@JoinRoomActivity, "파티가 꽉 찼습니다..", Toast.LENGTH_SHORT).show()

                } else{
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
                                    Toast.makeText(this@JoinRoomActivity, "파티 합류!", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@JoinRoomActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this@JoinRoomActivity, "이미 참가한 파티입니다", Toast.LENGTH_SHORT).show()
                                }
                            }

                        }

                        override fun onFailure(call: Call<joinRoomResponse>, t: Throwable) {
                            Log.e("API Request", "Error: ${t.message}")
                        }
                    })
                }
            }

        }
    }
}