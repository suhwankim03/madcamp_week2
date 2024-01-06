package com.example.findfriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.findfriend.databinding.ActivityCreateAccountBinding
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.findfriend.ui.Signup
import com.example.findfriend.ui.SignupResponse
import com.example.findfriend.ui.SignupService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private lateinit var binding: ActivityCreateAccountBinding
class CreateAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://143.248.199.213:5000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var SignupService = retrofit.create(SignupService::class.java)

        val button = binding.createAccountButton
        val id = binding.writeId
        val password = binding.writePassword
        val nickname = binding.writeNickname

        button.setOnClickListener {
            var textId = id.text.toString()
            var textPw = password.text.toString()
            var textNick = nickname.text.toString()

            val signup = Signup(id = textId, password = textPw, nickname = textNick)

            SignupService.requestSignup(signup).enqueue(object: Callback<SignupResponse>{
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    val successValue = response.body()
                    if (successValue != null) {
                        val issuc = successValue.success
                        if (issuc) {
                            Toast.makeText(applicationContext, "아이디 생성 성공", Toast.LENGTH_SHORT).show()
                            //아이디 생성 후 메인으로 이동
                            val intent = Intent(this@CreateAccountActivity, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(applicationContext, "아이디 중복", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.e("API Request", "Error: ${t.message}")
                }
            })
        }
    }
}