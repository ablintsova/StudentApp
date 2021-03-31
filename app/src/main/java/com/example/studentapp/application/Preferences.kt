package com.example.studentapp.application

import android.content.Context
import android.content.SharedPreferences

class Preferences (context: Context)
{
    private val DEF_STRING = ""
    private val STUDENT_ID = "student_id"
    private val STUDENT_NAME = "student_name"
    private val STUDENT_EMAIL = "student_email"
    private val STUDENT_PHONE = "student_phone"
    private val STUDENT_PHOTO = "student_photo"
    private val STUDENT_COURSE = "student_course"
    private val STUDENT_GROUP = "student_group"
    private val STUDENT_DEPARTMENT = "student_department"

    private val preferences: SharedPreferences = context.getSharedPreferences("Student", Context.MODE_PRIVATE)

    var studentId: Int
        get() = preferences.getInt(STUDENT_ID, -1)
        set(value) = preferences.edit().putInt(STUDENT_ID, value).apply()

    var studentName: String?
        get() = preferences.getString(STUDENT_NAME, DEF_STRING)
        set(value) = preferences.edit().putString(STUDENT_NAME, value).apply()

    var studentEmail: String?
        get() = preferences.getString(STUDENT_EMAIL, DEF_STRING)
        set(value) = preferences.edit().putString(STUDENT_EMAIL, value).apply()

    var studentPhone: String?
        get() = preferences.getString(STUDENT_PHONE, DEF_STRING)
        set(value) = preferences.edit().putString(STUDENT_PHONE, value).apply()

    var studentPhoto: String?
        get() = preferences.getString(STUDENT_PHOTO, DEF_STRING)
        set(value) = preferences.edit().putString(STUDENT_PHOTO, value).apply()

    var studentCourse: String?
        get() = preferences.getString(STUDENT_COURSE, DEF_STRING)
        set(value) = preferences.edit().putString(STUDENT_COURSE, value).apply()

    var studentGroup: String?
        get() = preferences.getString(STUDENT_GROUP, DEF_STRING)
        set(value) = preferences.edit().putString(STUDENT_GROUP, value).apply()

    var studentDepartment: String?
        get() = preferences.getString(STUDENT_DEPARTMENT, DEF_STRING)
        set(value) = preferences.edit().putString(STUDENT_DEPARTMENT, value).apply()
}