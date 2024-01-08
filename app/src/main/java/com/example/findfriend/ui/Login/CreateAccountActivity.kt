package com.example.findfriend.ui.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.findfriend.databinding.ActivityCreateAccountBinding
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.findfriend.Settings.GlobalApplication
import com.example.findfriend.Settings.GlobalApplication.Companion.signupService
import com.example.findfriend.ui.MainActivity
import com.example.findfriend.connectDB.Signup
import com.example.findfriend.connectDB.SignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = binding.createAccountButton
        val id = binding.writeId
        val password = binding.writePassword
        val nickname = binding.writeNickname

        button.setOnClickListener {
            var textId = id.text.toString()
            var textPw = password.text.toString()
            var textNick = nickname.text.toString()

            val signup = Signup(id = textId, password = textPw, nickname = textNick)

            signupService.requestSignup(signup).enqueue(object: Callback<SignupResponse>{
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    val successValue = response.body()
                    if (successValue != null) {
                        val issuc = successValue.success
                        if (issuc) {
                            GlobalApplication.prefs.setString("email", "${textId}")
                            GlobalApplication.prefs.setString("password", "${textPw}")
                            GlobalApplication.prefs.setString("nickname", "${textNick}")
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