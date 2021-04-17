package com.example.studentapp.model.entities

import com.example.studentapp.model.entities.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success")
    var success: String = "") {

    @SerializedName("user")
    var user: User? = null

    override fun toString(): String {
        return "success: $success, user: { $user }"
    }
}