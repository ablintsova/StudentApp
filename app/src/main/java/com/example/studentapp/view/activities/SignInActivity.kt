package com.example.studentapp.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.R
import com.example.studentapp.presenter.SignInPresenter
import com.example.studentapp.presenter.SignInPresenterInterface
import com.example.studentapp.view.interfaces.SignInViewInterface

const val SIGN_IN_TAG = "SignInActivity"

class SignInActivity : AppCompatActivity(), SignInViewInterface {

    private lateinit var presenter: SignInPresenterInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        presenter = SignInPresenter(this)
        presenter.createApiConnection()

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            onLoginButtonPressed()
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private fun onLoginButtonPressed() {
        val login = findViewById<EditText>(R.id.etLogin).text.toString()
        val password = findViewById<EditText>(R.id.etPassword).text.toString()
        presenter.signIn(login, password)
    }

    override fun goToNavigationActivity() {
        val intent = Intent(this, NavigationActivity::class.java)
        startActivity(intent)
    }

    override fun showMessage(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}