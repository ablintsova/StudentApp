package com.example.studentapp.model.api

import retrofit2.Call
import retrofit2.http.*

interface TspuApi {

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("login") login: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}