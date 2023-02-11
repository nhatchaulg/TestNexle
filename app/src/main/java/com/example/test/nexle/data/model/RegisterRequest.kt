package com.example.test.nexle.data.model

import com.google.gson.annotations.SerializedName

class RegisterRequest (
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
)