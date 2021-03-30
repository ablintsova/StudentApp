package com.example.studentapp.model.entities

import com.google.gson.annotations.SerializedName

data class Training(
    @SerializedName("id")
    var id: Int,

    @SerializedName("course")
    var course: String,

    @SerializedName("group")
    var group: String,

    @SerializedName("department")
    var department: Department

) {
    override fun toString(): String {
        return "id: $id, course: $course, group: $group, department: { $department }"
    }
}