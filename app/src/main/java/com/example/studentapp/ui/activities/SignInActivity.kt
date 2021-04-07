package com.example.studentapp.ui.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.R
import com.example.studentapp.model.Constants
import com.example.studentapp.model.Network
import com.example.studentapp.model.api.login.LoginCallback
import com.example.studentapp.model.api.TspuApi

const val SIGN_IN_TAG = "SignInActivity"

class SignInActivity : AppCompatActivity() {

    private lateinit var tspuApi: TspuApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        tspuApi = Network().getRetrofitClient(Constants.LOGIN_URL).create(TspuApi::class.java)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            onLoginButtonPressed()
        }
    }

    private fun onLoginButtonPressed() {
        val login = findViewById<EditText>(R.id.etLogin).text.toString()
        val password = findViewById<EditText>(R.id.etPassword).text.toString()

        if (isStringValid(login)) {
            if (isStringValid(password)) {
                tspuApi.login(login, password).enqueue(LoginCallback(this))
            }
            else {
                Log.e(SIGN_IN_TAG, "onLoginButtonPressed: password is empty")
                showError("Укажите пароль")
            }
        } else {
            Log.e(SIGN_IN_TAG, "onLoginButtonPressed: login is empty")
            showError("Укажите логин")
        }
    }

    private fun isStringValid(str: String): Boolean {
        return !str.isNullOrEmpty()
    }

    private fun showError(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}