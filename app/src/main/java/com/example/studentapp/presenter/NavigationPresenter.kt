package com.example.studentapp.presenter

import android.util.Log
import com.example.studentapp.application.prefs
import com.example.studentapp.model.api.TspuApi
import com.example.studentapp.model.entities.Group
import com.example.studentapp.model.entities.Lesson
import com.example.studentapp.utils.Constants
import com.example.studentapp.utils.Network
import com.example.studentapp.view.activities.NAV_TAG
import com.example.studentapp.view.interfaces.NavigationViewInterface
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class NavigationPresenter(var view: NavigationViewInterface?): NavigationPresenterInterface {
    private lateinit var tspuApi: TspuApi

    override fun getApiData() {
        tspuApi = Network().getRetrofitClient(Constants.TIMETABLE_URL).create(TspuApi::class.java)
        Log.d(NAV_TAG, "onSchedule: ready to get groups")
        getGroups()
        Log.d(NAV_TAG, "onSchedule: ready to get timetable")
        getTimetable()
    }

    override fun onDestroy() {
        view = null
    }

    private fun getGroups() {
        tspuApi.getGroups().enqueue(object : retrofit2.Callback<List<Group>> {
            override fun onFailure(call: Call<List<Group>>, t: Throwable) {
                Log.e("groups onFailure", "error uploading: ${t.message}}")
                view?.showMessage("Возникла ошибка при получении списка групп!")
            }

            override fun onResponse(
                call: Call<List<Group>>,
                response: Response<List<Group>>
            ) {
                val body = response.body()
                val errorBody = response.errorBody()
                Log.d("groups onResponse", "response body: $body")
                Log.e("groups onResponse", "response error: $errorBody.")

                if (body != null && errorBody == null) {
                    onSuccess(response)
                } else {
                    view?.showMessage( "Неизвестная ошибка при получении списка групп! Попробуйте ещё раз")
                }
            }

            private fun onSuccess(response: Response<List<Group>>) {
                val gson = Gson()
                response.body()?.let {
                    prefs.groups = gson.toJson(it)
                }
            }
        })
    }

    private fun getTimetable() {
        tspuApi.getTimetable("493").enqueue(object : retrofit2.Callback<List<Lesson>>  {
            override fun onFailure(call: Call<List<Lesson>>, t: Throwable) {
                Log.e("timetable onFailure", "error uploading: ${t.message}}")
                view?.showMessage("Возникла ошибка при получении расписания!")
            }

            override fun onResponse(
                call: Call<List<Lesson>>,
                response: Response<List<Lesson>>
            ) {
                val body = response.body()
                val errorBody = response.errorBody()
                Log.d("timetable onResponse", "response body: $body")
                Log.e("timetable onResponse", "response error: $errorBody.")

                if (body != null && errorBody == null) {
                    onSuccess(response)
                } else {
                    view?.showMessage("Неизвестная ошибка при получении расписания! Попробуйте ещё раз")
                }
            }

            private fun onSuccess(response: Response<List<Lesson>>) {
                val gson = Gson()
                response.body()?.let {
                    prefs.timetable = gson.toJson(it)
                }
            }
        })
    }
}