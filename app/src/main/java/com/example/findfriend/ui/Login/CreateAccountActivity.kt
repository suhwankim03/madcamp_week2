package com.example.findfriend.ui.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.findfriend.databinding.ActivityCreateAccountBinding
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
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

        //글자수 제한 설정.
        val inputFilter = InputFilter.LengthFilter(7)

        id.filters = arrayOf(inputFilter)
        id.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.idnum.text = "0 / 7"
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var textId = id.text.toString()
                binding.idnum.text = textId.length.toString() + " / 7"
            }

            override fun afterTextChanged(p0: Editable?) {
                var textId = id.text.toString()
                binding.idnum.text = textId.length.toString() + " / 7"
            }

        })

        password.filters = arrayOf(inputFilter)
        password.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.pwnum.text = "0 / 7"
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var textPw = password.text.toString()
                binding.pwnum.text = textPw.length.toString() + " / 7"
            }

            override fun afterTextChanged(p0: Editable?) {
                var textPw = password.text.toString()
                binding.pwnum.text = textPw.length.toString() + " / 7"
            }

        })

        nickname.filters = arrayOf(inputFilter)
        nickname.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.nicknum.text = "0 / 7"
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var textNick = nickname.text.toString()
                binding.nicknum.text = textNick.length.toString() + " / 7"
            }

            override fun afterTextChanged(p0: Editable?) {
                var textNick = nickname.text.toString()
                binding.nicknum.text = textNick.length.toString() + " / 7"
            }

        })



        button.setOnClickListener {
            var textId = id.text.toString()
            var textPw = password.text.toString()
            var textNick = nickname.text.toString()

            if (textId.isEmpty()) {
                Toast.makeText(applicationContext, "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show()
            }else if (textPw.isEmpty()) {
                Toast.makeText(applicationContext, "비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show()
            }else if (textNick.isEmpty()) {
                Toast.makeText(applicationContext, "닉네임을 입력해 주세요", Toast.LENGTH_SHORT).show()
            }else {
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
                                finish()
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
}