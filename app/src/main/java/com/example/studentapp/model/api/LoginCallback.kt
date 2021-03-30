package com.example.studentapp.model.api

import android.util.Log
import com.example.studentapp.model.entities.Student
import com.example.studentapp.model.entities.User
import com.example.studentapp.ui.activities.SignInActivity
import retrofit2.Call
import retrofit2.Response

class LoginCallback(var context: SignInActivity) : retrofit2.Callback<LoginResponse> {

    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
        Log.d("login onFailure", "error uploading: ${t.message}}")
    }

    override fun onResponse(
        call: Call<LoginResponse>,
        response: Response<LoginResponse>
    ) {
        val body = response.body()
        val errorBody = response.errorBody()
        Log.d("login onResponse", "response body: $body")
        Log.e("login onResponse", "response error: $errorBody")
        var student = createStudent(response?.body()?.user)
        context.student = student


    }

    private fun createStudent(user: User?): Student {
        val student = Student()
        val u = user?.let {
            student.id = it.id
            student.name = it.name
            student.email = it.person.email
            student.phone = it.person.phone
            student.photo = it.person.photo
            student.course = it.person.training[0].course
            student.group = it.person.training[0].group
            student.department = it.person.training[0].department.title
        }
        return student
    }
}