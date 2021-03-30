package com.example.studentapp.model.entities

class Student {
    var id: Int = 0
    var name:String = ""
    var email: String = ""
    var phone: String = ""
    var photo: String = ""
    var course: String = ""
    var group: String = ""
    var department: String = ""

    override fun toString(): String {
        return "id: $id, name: $name, email: $email, phone: $phone, photo: $photo, course: $course, group: $group, department: $department"
    }
}