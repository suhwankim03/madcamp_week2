package com.example.findfriend.Settings

import android.app.Application
import com.example.findfriend.BuildConfig
import com.example.findfriend.connectDB.RoomService
import com.example.findfriend.connectDB.SignupService
import com.google.gson.GsonBuilder
import com.kakao.sdk.common.KakaoSdk
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GlobalApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
        lateinit var retrofit: Retrofit
        lateinit var roomService: RoomService
        lateinit var signupService: SignupService
    }

    override fun onCreate() {
        super.onCreate()
        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)

        //기기 내부 저장소 sharepreference
        prefs = PreferenceUtil(applicationContext)

        retrofit = Retrofit.Builder()
            .baseUrl("http://143.248.199.213:5000")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()

        roomService = retrofit.create(RoomService::class.java)
        signupService = retrofit.create(SignupService::class.java)
    }
}