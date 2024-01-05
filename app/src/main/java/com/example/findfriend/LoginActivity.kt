package com.example.findfriend

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.findfriend.databinding.ActivityLoginBinding
import com.kakao.sdk.common.util.Utility
import org.json.JSONObject

private lateinit var binding: ActivityLoginBinding
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val loginButton = binding.loginBackGroundLogin
        val createAccountButton = binding.loginBackGroundCreateAccount
        val loginByKaKaoButton = binding.loginBackGroundLoginByKaKao

        loginButton.setOnClickListener{

            val idData = binding.writeId.text.toString()
            val passwordData = binding.writePassword.text.toString()
            val jsonData = JSONObject().apply {
                put("id", idData)
                put("password",passwordData)
            }

            //"입력한 아이디 패스워드가 서버에 있는지 확인하는 내용 필요"
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        createAccountButton.setOnClickListener{

        }

        loginByKaKaoButton.setOnClickListener{

        }
    }



}