package com.example.studentapp.presenter

import android.util.Log
import com.example.studentapp.application.prefs
import com.example.studentapp.model.api.TspuApi
import com.example.studentapp.model.entities.Lesson
import com.example.studentapp.presenter.contracts.SchedulePresenterContract
import com.example.studentapp.utils.Constants
import com.example.studentapp.utils.Network
import com.example.studentapp.view.contracts.NavigationViewContract
import com.example.studentapp.view.contracts.ScheduleViewContract
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

class SchedulePresenter(var view: ScheduleViewContract?, var activity: NavigationViewContract?): SchedulePresenterContract {

    private lateinit var tspuApi: TspuApi

    override fun onGroupSelected(group: String) {
        tspuApi = Network().getRetrofitClient(Constants.TIMETABLE_URL).create(TspuApi::class.java)
        tspuApi.getTimetable(group).enqueue(object : retrofit2.Callback<List<Lesson>>  {
            override fun onFailure(call: Call<List<Lesson>>, t: Throwable) {
                Log.e("timetable onFailure", "error uploading: ${t.message}}")
                activity?.showMessage("Возникла ошибка при получении расписания!")
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
                    activity?.showMessage("Неизвестная ошибка при получении расписания! Попробуйте ещё раз")
                }
            }

            private fun onSuccess(response: Response<List<Lesson>>) {
                val gson = Gson()
                response.body()?.let {
                    prefs.timetable = gson.toJson(it)
                }
                view?.showTimetable()
            }
        })
    }

    override fun onDestroy() {
        activity = null
        view = null
    }
}