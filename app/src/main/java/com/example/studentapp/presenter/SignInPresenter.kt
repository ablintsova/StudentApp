package com.example.studentapp.presenter

import android.util.Log
import com.example.studentapp.application.prefs
import com.example.studentapp.utils.Constants
import com.example.studentapp.utils.Network
import com.example.studentapp.model.api.TspuApi
import com.example.studentapp.model.entities.LoginResponse
import com.example.studentapp.model.entities.User
import com.example.studentapp.presenter.contracts.SignInPresenterContract
import com.example.studentapp.view.activities.SIGN_IN_TAG
import com.example.studentapp.view.contracts.SignInViewContract
import retrofit2.Call
import retrofit2.Response

class SignInPresenter(var view: SignInViewContract?): SignInPresenterContract {

    private lateinit var tspuApi: TspuApi

    override fun createApiConnection() {
        tspuApi = Network().getRetrofitClient(Constants.LOGIN_URL).create(TspuApi::class.java)
    }

    override fun signIn(login: String, password: String) {
        if (areCredentialsValid(login, password)) {
                tspuApi.login(login, password).enqueue(object : retrofit2.Callback<LoginResponse> {

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.d("signIn onFailure", "error uploading: ${t.message}}")
                        view?.showMessage("Возникла ошибка! Попробуйте ещё раз")
                    }

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        val body = response.body()
                        val errorBody = response.errorBody()
                        Log.d("signIn onResponse", "response body: $body")
                        Log.e("signIn onResponse", "response error: $errorBody.")

                        if (body != null && errorBody == null) {
                            onSuccess(response)
                        } else if (response.code() == 401) {
                            view?.showMessage("Ошибка! Проверьте правильность логина и пароля")
                        } else {
                            view?.showMessage("Неизвестная ошибка! Попробуйте ещё раз")
                        }
                    }

                    private fun onSuccess(response: Response<LoginResponse>) {
                        response.body()?.user?.let {
                            saveStudent(it)
                        }
                        view?.goToNavigationActivity()
                    }
                })
            }
    }

    override fun onDestroy() {
        view = null
    }

    private fun areCredentialsValid(login: String, password: String): Boolean {
        if (login.isNotEmpty()) {
            if (password.isNotEmpty()) {
                return true
            }
            else {
                Log.e(SIGN_IN_TAG, "onLoginButtonPressed: password is empty")
                view?.showMessage("Укажите пароль")
            }
        } else {
            Log.e(SIGN_IN_TAG, "onLoginButtonPressed: login is empty")
            view?.showMessage("Укажите логин")
        }
        return false
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