package com.example.findfriend.ui.FindRoom

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.example.findfriend.Settings.GlobalApplication
import com.example.findfriend.Settings.GlobalApplication.Companion.roomService
import com.example.findfriend.databinding.ActivityAddRoomBinding
import com.example.findfriend.connectDB.addRoom
import com.example.findfriend.connectDB.addRoomResponse
import com.example.findfriend.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class AddRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRoomBinding
    private lateinit var selectedDate: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val roomName = binding.writeRoomName
        val roomDetail = binding.writeRoomDetail
        val limitTime = binding.writeLimitTime
        val location= binding.writeLocation
        val maxNum= binding.writeMaxNumber
        val current_people:Int = 0
        val myID = GlobalApplication.prefs.getString("email","email 검색 오류")
        val myNickname = GlobalApplication.prefs.getString("nickname","nickname 검색 오류")
        val completeButton =binding.completeButton
        val goBackButton = binding.backButton

        goBackButton.setOnClickListener {
            val intent = Intent(this@AddRoomActivity, MainActivity::class.java)
            startActivity(intent)
            //FindRoomFragment로 이동할 수 있게 코드 추가하면 좋을듯
        }

        limitTime.setOnClickListener {
            showDatePickerDialog()
        }

        completeButton.setOnClickListener {
            var txtroomName = roomName.text.toString()
            var txtroomDetail = roomDetail.text.toString()
            var txtlimTime = limitTime.text.toString()
            var txtLocation = location.text.toString()
            var txtmaxPeople = maxNum.text.toString()

            if (txtroomName.isEmpty()) {
                Toast.makeText(applicationContext, "파티명을 정해주세요", Toast.LENGTH_SHORT).show()
            }else if (txtlimTime.isEmpty()) {
                Toast.makeText(applicationContext, "만날 시간을 정해주세요", Toast.LENGTH_SHORT).show()
            /*}else if (!isNumeric(txtlimTime)) {
                Toast.makeText(applicationContext, "만날 시간은 숫자로 작성해주세요", Toast.LENGTH_SHORT).show()*/
            }else if (txtmaxPeople.isEmpty()) {
                Toast.makeText(applicationContext, "최대 인원을 정해주세요", Toast.LENGTH_SHORT).show()
            }else if (!isNumeric(txtmaxPeople)) {
                Toast.makeText(applicationContext, "최대 인원은 숫자로 작성해주세요", Toast.LENGTH_SHORT).show()
            }else if (txtroomDetail.isEmpty()) {
                Toast.makeText(applicationContext, "파티 소개를 작성해주세요", Toast.LENGTH_SHORT).show()
            }else if (txtLocation.isEmpty()) {
                Toast.makeText(applicationContext, "만날 장소를 정해주세요", Toast.LENGTH_SHORT).show()
            }else{
                val newRoom = addRoom(roomName = txtroomName, roomDetail = txtroomDetail, limTime = txtlimTime, location = txtLocation, maxPeople = txtmaxPeople.toInt(), minPeople = current_people, owner = myID, ownerNickname = myNickname)
                roomService.requestAddRoom(newRoom).enqueue(object: Callback<addRoomResponse> {
                    override fun onResponse(
                        call: Call<addRoomResponse>,
                        response: Response<addRoomResponse>
                    ) {
                        val successValue = response.body()
                        if (successValue != null) {
                            val issuc = successValue.success
                            if (issuc) {
                                Toast.makeText(this@AddRoomActivity, "파티 생성!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@AddRoomActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@AddRoomActivity, "파티 생성 실패!", Toast.LENGTH_SHORT).show()
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
    private fun isNumeric(input: String): Boolean {
        return input.toIntOrNull() != null
    }


    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                binding.writeLimitTime.setText(selectedDate)
                showTimePickerDialog()
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                val selectedTime = "$selectedHour:$selectedMinute"
                binding.writeLimitTime.setText("$selectedDate $selectedTime")
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }
}