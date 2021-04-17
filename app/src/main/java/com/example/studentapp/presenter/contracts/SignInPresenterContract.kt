package com.example.studentapp.presenter.contracts

interface SignInPresenterContract: BasePresenterContract {
    fun createApiConnection()
    fun signIn(login: String, password: String)
}
