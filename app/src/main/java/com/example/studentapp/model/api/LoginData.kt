package com.example.studentapp.model.api

import com.google.gson.annotations.SerializedName

class LoginData {

    // Example data
    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("error")
    var error: String? = null

    override fun toString(): String {
        return "title: $title, description: $description, error: $error"
    }
}