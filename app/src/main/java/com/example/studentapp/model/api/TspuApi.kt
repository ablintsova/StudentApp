package com.example.studentapp.model.api

import com.example.studentapp.model.api.login.LoginResponse
import com.example.studentapp.model.entities.Group
import com.example.studentapp.model.entities.Lesson
import retrofit2.Call
import retrofit2.http.*

interface TspuApi {

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("login") login: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("rasp_teacher.php")
    fun getTimetable(
        @Query("group") group: String
    ): Call<List<Lesson>>

    @GET("grs.php")
    fun getGroups(): Call<List<Group>>
}