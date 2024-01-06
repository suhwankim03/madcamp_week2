package com.example.findfriend

import androidx.appcompat.app.AppCompatActivity
import com.example.findfriend.databinding.ActivityCreateAccountBinding
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.findfriend.ui.Login
import com.example.findfriend.ui.LoginResponse

import com.example.findfriend.ui.LoginService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private lateinit var binding: ActivityCreateAccountBinding
class CreateAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://143.248.199.213:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var loginService = retrofit.create(LoginService::class.java)

        val button = findViewById<Button>(R.id.createAccount_login)
        val editText = findViewById<EditText>(R.id.writeId)
        val editText2 = findViewById<EditText>(R.id.writePassword)
        val editText3 = findViewById<EditText>(R.id.writeNickname)
        button.setOnClickListener {
            var textId = editText.text.toString()
            var textPw = editText2.text.toString()
            var textNick = editText3.text.toString()

            val login = Login(id = textId, password = textPw, nickname = textNick)

            loginService.requestLogin(login).enqueue(object: Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val successValue = response.body()
                    if (successValue != null) {
                        val issuc = successValue.success
                        if (issuc) {
                            Toast.makeText(applicationContext, "성공", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(applicationContext, "아이디 중복", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("API Request", "Error: ${t.message}")
                }




            })
        }
    }
}