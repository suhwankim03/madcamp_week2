package com.example.findfriend.ui



data class Login(
    var id: String,
    var password: String,
    var nickname: String
)

data class LoginResponse(
    val success: Boolean
)

