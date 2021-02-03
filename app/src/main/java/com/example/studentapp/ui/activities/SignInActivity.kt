package com.example.studentapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.studentapp.R

class SignInActivity : AppCompatActivity() {
    lateinit var webView: WebView
    val url = "https://local.tspu.edu.ru/portal/login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        setWebView()

        /*findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val intent = Intent(this, NavigationActivity::class.java)
            startActivity(intent)
        }*/
    }

    private fun setWebView() {
        webView = findViewById(R.id.browser)
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            // Override URL
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url == "https://local.tspu.edu.ru/portal/") {
                    val intent = Intent(applicationContext, NavigationActivity::class.java)
                    startActivity(intent)
                    return true
                }
                return false
            }
        }
        webView.loadUrl(url)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        }
    }
}