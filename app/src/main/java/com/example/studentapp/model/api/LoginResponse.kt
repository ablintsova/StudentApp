package com.example.studentapp.model.api

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success")
    var success: String = "") {


    override fun toString(): String {
        return "success: $success"
    }
}