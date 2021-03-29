package com.example.studentapp.model.api

import com.example.studentapp.model.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success")
    var success: String = "") {

    @SerializedName("token")
    var token: String = ""

    @SerializedName("user")
    var user: User? = null

    override fun toString(): String {
        return "success: $success, user: { $user }"
    }
}