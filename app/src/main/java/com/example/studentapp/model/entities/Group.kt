package com.example.studentapp.model.entities

import com.google.gson.annotations.SerializedName

data class Group(
    @SerializedName("gruppa")
    var number: String,

    @SerializedName("course")
    var course: String,

    @SerializedName("fakultet")
    var department: String
) {
    override fun toString(): String {
        return "number: $number, course: $course, department: $department"
    }
}