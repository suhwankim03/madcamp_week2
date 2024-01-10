package com.example.findfriend.ui.MyRoom

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.findfriend.R
import com.example.findfriend.Settings.GlobalApplication
import com.example.findfriend.Settings.GlobalApplication.Companion.prefs
import com.example.findfriend.databinding.ActivityChatBinding
import com.example.findfriend.ui.MainActivity
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URISyntaxException

class ChatActivity : AppCompatActivity() {


    private var mSocket: Socket? = null
    private var roomID: String? = null
    private lateinit var sendtext: TextView
    private lateinit var binding: ActivityChatBinding
    private lateinit var chatViewModel : ChatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        val inputFilter = InputFilter.LengthFilter(27)
        val Header = binding.Header
        val limitTime = binding.LimitTime
        val location= binding.Location
        val maxNum= binding.MaxNumber
        val ownerID= binding.owner
        val ownerNickname= binding.ownerNick
        val current_people = binding.currentNumber
        val goBackButton = binding.backButton
        val sendButton = binding.sendButton
        val editMessage = binding.sendMessage
        val chat = binding.ownerNick
        sendtext = binding.ownerNick
        editMessage.filters = arrayOf(inputFilter)
        chat.movementMethod=ScrollingMovementMethod.getInstance()


        goBackButton.setOnClickListener {
            val intent = Intent(this@ChatActivity, MainActivity::class.java)
            startActivity(intent)
        }

        sendButton.setOnClickListener {
            sendMessage()
        }

        //roomID.text = intent.getStringExtra("roomID")
        Header.text = intent.getStringExtra("roomName")
        limitTime.text = intent.getStringExtra("limTime")
        location.text = intent.getStringExtra("location")
        current_people.text = intent.getStringExtra("currentPeople")
        maxNum.text = intent.getStringExtra("maxPeople")
        ownerID.text = intent.getStringExtra("owner")
        ownerNickname.text = intent.getStringExtra("ownerNickname")

        init()
        getHistory()

        mSocket?.on("msg_to_client${roomID}", onMessage)
    }

    var onMessage = Emitter.Listener { args ->
        val obj = JSONObject(args[0].toString())
        val a = sendtext.text.toString()
        Log.d("main activity", obj.toString())

        if(sendtext.text.isNullOrEmpty()){
            Thread(object : Runnable{
                override fun run() {
                    runOnUiThread(Runnable {
                        kotlin.run {
                            sendtext.text = "" + obj.get("nickname") + " : " + obj.get("chat")
                        }
                    })
                }
            }).start()
        } else {
            Thread(object : Runnable{
                override fun run() {
                    runOnUiThread(Runnable {
                        kotlin.run {
                            sendtext.text = a + "\n" + obj.get("nickname") + " : " + obj.get("chat")
                        }
                    })
                }
            }).start()
        }
    }


    private fun sendMessage() {

        val obj = JSONObject()
        obj.put("room_id",roomID?.toInt())
        obj.put("id",prefs.getString("email","email 오류"))
        obj.put("nickname",prefs.getString("nickname","nickname 오류"))
        obj.put("chat",binding.sendMessage.text)

        mSocket?.emit("message", obj)
        binding.sendMessage.setText("")
    }

    private fun init() {
        val intent = intent
        roomID = intent.getStringExtra("roomID")
        try {
            mSocket = IO.socket("http://143.248.199.213:5000")
            Log.d("SOCKET", "${mSocket}")
            mSocket?.connect()

        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    private fun getHistory(){
        GlobalApplication.roomService.getChat(roomID!!).enqueue(object :
            Callback<List<RoomChatDataModel>> {
            override fun onResponse(
                call: Call<List<RoomChatDataModel>>,
                response: Response<List<RoomChatDataModel>>
            ) {
                Log.d("tag","$roomID")
                if (response.isSuccessful) {
                    Log.d("tag","response 성공")
                    Log.d("tag","$response")
                    val getChat = response.body()
                    Log.d("getChat","$getChat")
                    val stringBuilder = StringBuilder()
                    for (i in 0 until getChat!!.size) {
                        val message = getChat[i].message
                        Log.d("message","$message")
                        stringBuilder.append("$message\n")
                    }
                    val combinedMessages = stringBuilder.toString().trim()

                    runOnUiThread {
                        sendtext.text = combinedMessages
                    }

                } else {
                    // 서버 응답이 실패했을 때의 처리
                    Log.d("tag","response 실패")
                }
            }
            override fun onFailure(call: Call<List<RoomChatDataModel>>, t: Throwable) {
                Log.d("tag","네트워크 실패")
                //네트워크 응답 자체가 실패했을 때의 처리
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket?.disconnect()
    }
}