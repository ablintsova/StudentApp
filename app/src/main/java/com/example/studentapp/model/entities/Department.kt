package com.example.studentapp.model.entities

import com.google.gson.annotations.SerializedName

data class Department(
    @SerializedName("id")
    var id: Int,

    @SerializedName("title")
    var title: String
) {
    override fun toString(): String {
        return "id: $id,  title: $title"
    }

}
