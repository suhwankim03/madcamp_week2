package com.example.findfriend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.findfriend.R
import com.example.findfriend.databinding.ActivityCreateAccountBinding

private lateinit var binding: ActivityCreateAccountBinding
class CreateAccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
    }
}