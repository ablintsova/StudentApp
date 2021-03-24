package com.example.studentapp.model

data class User (
    var id: Int,
    var name: String,
    var department: String,
    var course: Int,
    var group: String,
    var email: String,
    var phone: String,
    var photo: String
)