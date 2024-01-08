package com.example.findfriend.Settings

import android.app.Application
import com.example.findfriend.BuildConfig
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()
        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
        prefs = PreferenceUtil(applicationContext)
    }
}