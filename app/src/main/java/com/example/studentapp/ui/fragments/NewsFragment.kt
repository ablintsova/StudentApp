package com.example.studentapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.studentapp.R
import com.example.studentapp.ui.activities.NAV_TAG

const val NEWS_TAG = "NewsFragment"

class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    private val url = "https://www.tspu.edu.ru/news"
    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.d(NEWS_TAG, "onCreateView: beginning")
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(NEWS_TAG, "onViewCreated: beginning")
        setWebView()
        Log.d(NEWS_TAG, "onViewCreated: end")
    }

    private fun setWebView() {
        Log.d(NEWS_TAG, "setWebView: beginning")

        webView = view?.findViewById(R.id.browser)!!
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {
            // Override URL
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url == "https://local.tspu.edu.ru/portal/") {

                    return true
                }
                return false
            }
        }
        webView.loadUrl(url)
    }
}