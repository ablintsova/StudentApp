package com.example.studentapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.R

const val SIGN_IN_TAG = "SignInActivity"

class SignInActivity : AppCompatActivity() {


    val url = "https://local.tspu.edu.ru/portal/login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            onLoginButtonPressed()
        }
    }

    private fun onLoginButtonPressed() {
        val login = findViewById<EditText>(R.id.etLogin).text.toString()
        val password = findViewById<EditText>(R.id.etPassword).text.toString()

        if (isStringValid(login) && isStringValid(password)) {
            // implement authorization
            Log.d(SIGN_IN_TAG, "onLoginButtonPressed: ready to login")
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
        }
        else {
            Log.e(SIGN_IN_TAG, "onLoginButtonPressed: one of the fields is empty")
        }
    }

    private fun isStringValid(str: String): Boolean {
        return !str.isNullOrEmpty()
    }
}