package com.example.studentapp.model.api.groups

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.studentapp.model.entities.Group
import com.example.studentapp.ui.activities.NavigationActivity
import retrofit2.Call
import retrofit2.Response

class GroupsCallback(var context: NavigationActivity): retrofit2.Callback<List<Group>>  {
    override fun onFailure(call: Call<List<Group>>, t: Throwable) {
        Log.e("groups onFailure", "error uploading: ${t.message}}")
        Toast.makeText(context, "Возникла ошибка при получении списка групп!", Toast.LENGTH_LONG).show()
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
            //onSuccess(response)
            Toast.makeText(context, response.body()?.toString(), Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(context, "Неизвестная ошибка! Попробуйте ещё раз", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun onSuccess(response: Response<List<Group>>) {
        /* val u = response.body()?.user?.let {
             saveStudent(it)
         }*/
        val intent = Intent(context, NavigationActivity::class.java)
        ContextCompat.startActivity(context, intent, null)
    }
}