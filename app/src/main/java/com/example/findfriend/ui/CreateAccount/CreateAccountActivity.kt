package com.example.findfriend.ui.CreateAccount

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.findfriend.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.findfriend.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        binding = ActivityCreateAccountBinding.inflate(layoutInflater)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://143.248.199.213:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var loginService = retrofit.create(LoginService::class.java)

        val button = binding.createAccountLogin
        val id = binding.writeId
        val password = binding.writePassword
        val nickname = binding.writeNickname
        button.setOnClickListener {
            var textId = id.text.toString()
            var textPw = password.text.toString()
            var textNick = nickname.text.toString()

            val login = Login(id = textId, password = textPw, nickname = textNick)

            loginService.requestLogin(login).enqueue(object: Callback<Login> {
                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    var login = response.body()

                }

                override fun onFailure(call: Call<Login>, t: Throwable) {
                    Log.e("API Request", "Error: ${t.message}")
                }

            })
        }
    }
}