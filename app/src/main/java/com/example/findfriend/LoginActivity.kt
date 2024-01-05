package com.example.findfriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


lateinit var loginButton: Button
lateinit var createAccountButton: Button
lateinit var loginByKaKaoButton: Button
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val loginButton = findViewById<Button>(R.id.loginBackGround_login)
        val createAccountButton = findViewById<Button>(R.id.loginBackGround_createAccount)
        val loginByKaKaoButton = findViewById<Button>(R.id.loginBackGround_loginByKaKao)

        loginButton.setOnClickListener{
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