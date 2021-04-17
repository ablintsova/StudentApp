package com.example.studentapp.presenter

interface SignInPresenterInterface: BasePresenterInterface {
    fun createApiConnection()
    fun signIn(login: String, password: String)
}
