package com.example.studentapp.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    //var department: String,
    //var course: Int,
    //var group: String,

    @SerializedName("email")
    var email: String,
    //var phone: String,
    //var photo: String
) {
    override fun toString(): String {
        return "id: $id, name: $name, email: $email"
    }
}