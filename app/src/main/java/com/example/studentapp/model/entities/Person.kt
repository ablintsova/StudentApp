package com.example.studentapp.model.entities

import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("id")
    var id: Int,

    @SerializedName("email")
    var email: String,

    @SerializedName("phone")
    var phone: String = "",

    @SerializedName("photo")
    var photo: String = "",

    @SerializedName("trained")
    var training: List<Training>

) {
    override fun toString(): String {
        return "id: $id, email: $email, phone: $phone, photo: $photo, training: { $training }"
    }
}