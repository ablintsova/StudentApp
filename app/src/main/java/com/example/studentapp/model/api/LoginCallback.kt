package com.example.studentapp.model.api

import android.util.Log
import android.widget.Toast
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
        Toast.makeText(context, "response body: $body", Toast.LENGTH_LONG).show()
    }
}