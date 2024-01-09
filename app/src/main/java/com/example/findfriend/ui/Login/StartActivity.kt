package com.example.findfriend.ui.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.findfriend.R
import com.example.findfriend.Settings.GlobalApplication.Companion.prefs
import com.example.findfriend.ui.MainActivity

lateinit var fadeInAnim : Animation
lateinit var appLogo : ImageView
lateinit var appTitle: TextView


class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        appTitle = findViewById(R.id.appTitle)
        appLogo = findViewById(R.id.appLogo)
        appLogo.startAnimation(fadeInAnim)
        appTitle.startAnimation(fadeInAnim)

        if(prefs.getString("email"," ")!=" "){
            // 내부 저장소에 아이디가 있을 경우 바로 메인으로 연결
            Handler(Looper.getMainLooper()).postDelayed({
                val intent= Intent( this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 1500) // 1.5초 이후 실행
        } else {
            // 내부 저장소에 아이디가 없을 경우 로그인 화면으로 연결
            Handler(Looper.getMainLooper()).postDelayed({
                val intent= Intent( this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 1500) // 1.5초 이후 실행
        }

    }
}