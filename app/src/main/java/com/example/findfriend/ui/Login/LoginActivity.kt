package com.example.findfriend.ui.Login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.findfriend.Settings.GlobalApplication.Companion.prefs
import com.example.findfriend.Settings.GlobalApplication.Companion.signupService
import com.example.findfriend.ui.MainActivity
import com.example.findfriend.databinding.ActivityLoginBinding
import com.example.findfriend.connectDB.Login
import com.example.findfriend.connectDB.LoginResponse
import com.example.findfriend.connectDB.Signup
import com.example.findfriend.connectDB.SignupResponse
import com.example.findfriend.connectDB.SignupService
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private lateinit var binding: ActivityLoginBinding
private lateinit var kakaoEmail: String
private lateinit var kakaoId: String
private lateinit var kakaonickname: String
private lateinit var kakaoprofile: String


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id= binding.writeId
        val password= binding.writePassword
        val loginButton = binding.loginLogin
        val createAccountButton = binding.loginCreateAccount
        val loginByKaKaoButton = binding.loginLoginByKaKao

        //로그인 버튼 클릭
        loginButton.setOnClickListener {
            var textId = id.text.toString()
            var textPw = password.text.toString()

            val login = Login(id = textId, password = textPw)

            signupService.requestLogin(login).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val successValue = response.body()
                        if (successValue != null) {
                            val issuc = successValue.success
                            if (issuc) {
                                prefs.setString("email", "${textId}")
                                prefs.setString("password", "${textPw}")
                                Toast.makeText(applicationContext, "로그인", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    applicationContext,
                                    "잘못된 로그인 시도입니다",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "서버 응답이 실패했습니다.(${response.code()})",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("API Request", "Error: ${t.message}")
                }
            })
        }

        //계정 생성 버튼 클릭
        createAccountButton.setOnClickListener{
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }


            //카카오 로그인 버튼 클릭
        loginByKaKaoButton.setOnClickListener{
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    Log.i(TAG, "loginWithKakaoTalk $token $error")
                    updateLoginInfo()
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
                    Log.i(TAG, "loginWithKakaoAccount $token $error")
                    updateLoginInfo()
                }
            }
        }
    }

    private fun updateLoginInfo() {
        UserApiClient.instance.me { user, error ->
            user?.let {
                Log.i(TAG, "updateLoginInfo: ${user.id} ${user.kakaoAccount?.email} ${user.kakaoAccount?.profile?.nickname} ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                kakaoEmail = user.kakaoAccount?.email.toString()
                kakaonickname = user.kakaoAccount?.profile?.nickname.toString()
                kakaoprofile = user.kakaoAccount?.profile?.thumbnailImageUrl.toString()
                kakaoId = user.id.toString()

                val kakaologin = Signup(id = kakaoEmail, password = kakaoId, nickname = kakaonickname)

                signupService.requestSignup(kakaologin).enqueue(object: Callback<SignupResponse>{
                    override fun onResponse(
                        call: Call<SignupResponse>,
                        response: Response<SignupResponse>
                    ) {
                        prefs.setString("email", "${kakaoEmail}")
                        prefs.setString("password", "${kakaoId}")
                        prefs.setString("nickname", "${kakaonickname}")
                        val successValue = response.body()
                        if (successValue != null) {
                            val issuc = successValue.success
                            if (issuc) {
                                Toast.makeText(applicationContext, "카카오톡 회원가입", Toast.LENGTH_SHORT).show()
                                //아이디 생성 후 메인으로 이동
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(applicationContext, "카카오톡 로그인", Toast.LENGTH_SHORT).show()
                                //메인으로 이동
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                            }
                        }

                    }
                    override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                        Log.e("API Request", "Error: ${t.message}")
                    }
                })


                //binding.nicknametest.text = user.kakaoAccount?.profile?.nickname
                //Glide.with(this).load(user.kakaoAccount?.profile?.thumbnailImageUrl).circleCrop().into(binding.profiletest)
            }
            error?.let {
                //binding.nicknametest.text = null
                //binding.profiletest.setImageBitmap(null)
            }
        }
    }

}