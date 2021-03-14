package com.example.studentapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.R

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
            // TODO: implement authorization
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isStringValid(str: String): Boolean {
        return !str.isNullOrEmpty()
    }
}