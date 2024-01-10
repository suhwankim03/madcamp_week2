package com.example.findfriend.ui.MyRoom

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URISyntaxException

class ChatActivity : AppCompatActivity() {


    private var mSocket: Socket? = null
    private var roomID: String? = null
    private var ownerNickname: String? = null
    private lateinit var sendtext: TextView
    private lateinit var scrollView : ScrollView
    private lateinit var binding: ActivityChatBinding
    private lateinit var chatViewModel : ChatViewModel
    private lateinit var gestureDetector: GestureDetector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)

        val inputFilter = InputFilter.LengthFilter(27)
        val Header = binding.Header
        val goBackButton = binding.backButton
        val sendButton = binding.sendButton
        val editMessage = binding.sendMessage
        val chat = binding.ownerNick
        scrollView = binding.scrollView
        sendtext = binding.ownerNick
        editMessage.filters = arrayOf(inputFilter)


        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                scrollView.post {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN)
                }
                return true
            }
        })

        binding.detailButton.setOnClickListener {
            showCardViewDialog()
        }

        scrollView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }

        goBackButton.setOnClickListener {
            val intent = Intent(this@ChatActivity, MainActivity::class.java)
            startActivity(intent)
        }

        sendButton.setOnClickListener {
            sendMessage()
        }

        //roomID.text = intent.getStringExtra("roomID")
        Header.text = intent.getStringExtra("roomName")

        init()
        getHistory()

        mSocket?.on("msg_to_client${roomID}", onMessage)

        runOnUiThread {
            scrollView.post {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
            }
        }
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

        runOnUiThread {
            scrollView.post {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
            }
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
        runOnUiThread {
            scrollView.post {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN)
            }
        }
    }

    private fun init() {
        val intent = intent
        roomID = intent.getStringExtra("roomID")
        ownerNickname = intent.getStringExtra("ownerNickname")
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
                        scrollView.post {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN)
                        }
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

    @SuppressLint("MissingInflatedId")
    private fun showCardViewDialog() {
        // 다이얼로그 레이아웃을 가져오기
        val dialogView = LayoutInflater.from(this).inflate(R.layout.myroomdetail_cardview, null)

        // 다이얼로그 내부의 TextView에 원하는 정보 설정
       // dialogView.textViewDialogContent.text = "This is the content of the dialog."
        val limitTimeView = dialogView.findViewById<TextView>(R.id.LimitTime)
        val locationView = dialogView.findViewById<TextView>(R.id.Location)
        val maxNumView = dialogView.findViewById<TextView>(R.id.MaxNumber)
        val ownerView = dialogView.findViewById<TextView>(R.id.owner_nick)
        val roomDetailView = dialogView.findViewById<TextView>(R.id.RoomDetail)

        limitTimeView.text = intent.getStringExtra("limTime")
        locationView.text = intent.getStringExtra("location")
        maxNumView.text = intent.getStringExtra("maxPeople")
        ownerView.text = intent.getStringExtra("owner_nick")
        roomDetailView.text = intent.getStringExtra("roomDetail")

        // AlertDialog 생성 및 설정
        val alertDialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)

        // 다이얼로그 표시
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mSocket?.disconnect()
    }
}