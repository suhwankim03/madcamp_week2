package com.example.findfriend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.findfriend.databinding.ActivityLoginBinding
import com.example.findfriend.ui.CreateAccount.CreateAccountActivity

private lateinit var binding: ActivityLoginBinding
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginButton = binding.loginLogin
        val createAccountButton = binding.loginCreateAccount
        val loginByKaKaoButton = binding.loginLoginByKaKao

        loginButton.setOnClickListener{

/*
            val idData = binding.writeId.text.toString()
            val passwordData = binding.writePassword.text.toString()
            val jsonData = JSONObject().apply {
                put("id", idData)
                put("password",passwordData)
            }
*/

            //"입력한 아이디 패스워드가 서버에 있는지 확인하는 내용 필요"
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        createAccountButton.setOnClickListener{
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }

        loginByKaKaoButton.setOnClickListener{

        }
    }



}