package com.example.studentapp.model.api

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

import com.example.studentapp.application.prefs
import com.example.studentapp.model.entities.User
import com.example.studentapp.ui.activities.NavigationActivity
import com.example.studentapp.ui.activities.SignInActivity
import retrofit2.Call
import retrofit2.Response

class LoginCallback(var context: SignInActivity) : retrofit2.Callback<LoginResponse> {

    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
        Log.d("login onFailure", "error uploading: ${t.message}}")
        Toast.makeText(context, "Ошибка! Проверьте правильность логина и пароля", Toast.LENGTH_LONG).show()
    }

    override fun onResponse(
        call: Call<LoginResponse>,
        response: Response<LoginResponse>
    ) {
        val body = response.body()
        val errorBody = response.errorBody()
        Log.d("login onResponse", "response body: $body")
        Log.e("login onResponse", "response error: $errorBody")

        if (body != null && errorBody == null) {
            val u = response.body()?.user?.let {
                saveStudent(it)
            }
            val intent = Intent(context, NavigationActivity::class.java)
            startActivity(context, intent, null)
        } else {
            Toast.makeText(context, "Ошибка! Проверьте правильность логина и пароля", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun saveStudent(user: User) {
        prefs.studentId = user.id
        prefs.studentName = user.name
        prefs.studentEmail = user.person.email
        prefs.studentPhone = user.person.phone
        prefs.studentPhoto = user.person.photo
        prefs.studentCourse = user.person.training[0].course
        prefs.studentGroup = user.person.training[0].group
        prefs.studentDepartment = user.person.training[0].department.title
    }
}