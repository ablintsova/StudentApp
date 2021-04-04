package com.example.studentapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.studentapp.R
import com.example.studentapp.model.Constants

const val NEWS_TAG = "NewsFragment"

class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

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

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebView() {
        Log.d(NEWS_TAG, "setWebView: beginning")

        webView = view?.findViewById(R.id.browser)!!
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                view?.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('uk-sticky-placeholder')[0].style.display='none'; " +
                        "document.getElementsByClassName('uk-breadcrumb')[0].style.display='none';" +
                        "document.getElementsByClassName('tm-sidebar-a')[0].style.display='none';" +
                        "document.getElementsByClassName('tm-block tm-bottom tm-block-image')[0].style.display='none';})()")

            }
        }
        webView.loadUrl(Constants.NEWS_URL)
    }

    fun onBackPressed(): Boolean {
        if (webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return false
    }
}