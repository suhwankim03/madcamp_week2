package com.example.findfriend.ui.MyRoom

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
import java.net.URISyntaxException

class ChatActivity : AppCompatActivity() {

    private var mSocket: Socket? = null
    private var roomID: String? = null
    private val gson = Gson()

    private lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val Header = binding.Header
        val roomID = binding.roomID
        val roomDetail = binding.RoomDetail
        val limitTime = binding.LimitTime
        val location= binding.Location
        val maxNum= binding.MaxNumber
        val ownerID= binding.owner
        val ownerNickname= binding.ownerNick
        val current_people = binding.currentNumber
        val myID = GlobalApplication.prefs.getString("email","email 검색 오류")
        val goBackButton = binding.backButton
        val sendButton = binding.sendButton
        val editMessage = binding.sendMessage
        val chat = binding.ownerNick
        chat.movementMethod=ScrollingMovementMethod.getInstance()


        goBackButton.setOnClickListener {
            val intent = Intent(this@ChatActivity, MainActivity::class.java)
            startActivity(intent)
        }

        sendButton.setOnClickListener {
            sendMessage()
        }

        roomID.text = intent.getStringExtra("roomID")
        Header.text = intent.getStringExtra("roomName")
        limitTime.text = intent.getStringExtra("limTime")
        location.text = intent.getStringExtra("location")
        roomDetail.text = intent.getStringExtra("roomName")
        current_people.text = intent.getStringExtra("currentPeople")
        maxNum.text = intent.getStringExtra("maxPeople")
        ownerID.text = intent.getStringExtra("owner")
        ownerNickname.text = intent.getStringExtra("ownerNickname")

        init()
        mSocket?.on("msg_to_client", onMessage)
    }

    var onMessage = Emitter.Listener { args ->
        val sendtext: TextView = findViewById(R.id.owner_nick) as TextView
        val obj = JSONObject(args[0].toString())
        val a = sendtext.text.toString()
        Log.d("main activity", obj.toString())
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

    override fun onDestroy() {
        super.onDestroy()
        mSocket?.disconnect()
    }
}