package com.example.studentapp.model.api.timetable

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.studentapp.model.entities.Lesson
import com.example.studentapp.ui.activities.NavigationActivity
import retrofit2.Call
import retrofit2.Response

class TimetableCallback(var context: NavigationActivity): retrofit2.Callback<List<Lesson>>  {
    override fun onFailure(call: Call<List<Lesson>>, t: Throwable) {
        Log.e("timetable onFailure", "error uploading: ${t.message}}")
        Toast.makeText(context, "Возникла ошибка при получении расписания!", Toast.LENGTH_LONG).show()
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
            //onSuccess(response)
            Toast.makeText(context, response.body()?.toString(), Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(context, "Неизвестная ошибка! Попробуйте ещё раз", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun onSuccess(response: Response<List<Lesson>>) {
       /* val u = response.body()?.user?.let {
            saveStudent(it)
        }*/
        val intent = Intent(context, NavigationActivity::class.java)
        ContextCompat.startActivity(context, intent, null)
    }
}