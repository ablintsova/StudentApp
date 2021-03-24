package com.example.studentapp.model.api

import android.util.Log
import retrofit2.Call
import retrofit2.Response

class LoginCallback : retrofit2.Callback<LoginResponse> {

    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
        Log.d("login", "error uploading: ${t.message}}")
    }

    override fun onResponse(
        call: Call<LoginResponse>,
        response: Response<LoginResponse>
    ) {
        val body = response.body()
        val errorBody = response.errorBody()
        Log.d("login", "response body: $body")
        Log.e("login", "response error: $errorBody")
    }
}