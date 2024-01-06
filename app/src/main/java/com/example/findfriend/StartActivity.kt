package com.example.findfriend

import android.content.Intent
import android.icu.text.CaseMap.Title
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

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

        // 일정 시간 지연 이후 실행하기 위한 코드
        Handler(Looper.getMainLooper()).postDelayed({

            // 일정 시간이 지나면 LoginActivity로 이동
            val intent= Intent( this,LoginActivity::class.java)
            startActivity(intent)

            // 이전 키를 눌렀을 때 스플래스 스크린 화면으로 이동을 방지하기 위해
            // 이동한 다음 사용안함으로 finish 처리
            finish()

        }, 1000) // 시간 1초 이후 실행

    }
}