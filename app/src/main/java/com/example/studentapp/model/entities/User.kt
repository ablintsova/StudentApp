package com.example.studentapp.model.entities

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    var id: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("person")
    var person: Person
) {
    override fun toString(): String {
        return "id: $id, name: $name, person: { $person }"
    }
}