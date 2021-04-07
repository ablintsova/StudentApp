package com.example.studentapp.model.entities

import com.google.gson.annotations.SerializedName

class Lesson (
    @SerializedName("id")
    var id: String,

    @SerializedName("group")
    var group: String,

    @SerializedName("title")
    var title: String,

    @SerializedName("subgroup")
    var subgroup: String,

    @SerializedName("num_les")
    var lessonIndex: String,

    @SerializedName("start")
    var start: String,

    @SerializedName("end")
    var end: String,

    @SerializedName("teacher")
    var teacher: String,

    @SerializedName("teacher_id")
    var teacherId: String,

    @SerializedName("class")
    var room: String
    )
{
    override fun toString(): String {
        return "id: $id, group: $group, title: $title, subgroup: $subgroup, lessonIndex: $lessonIndex, start: $start, end: $end, teacher: $teacher, teacherId: $teacherId, room: $room"
    }
}