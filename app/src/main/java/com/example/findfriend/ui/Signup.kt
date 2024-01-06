package com.example.findfriend.ui



data class Signup(
    var id: String,
    var password: String,
    var nickname: String
)

data class SignupResponse(
    val success: Boolean
)

data class Login(
    var id: String,
    var password: String
)

data class LoginResponse(
    val success: Boolean
)
