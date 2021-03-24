package com.example.studentapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.Executors
import javax.net.ssl.*

import com.example.studentapp.R
import com.example.studentapp.model.Constants
import com.example.studentapp.model.api.LoginCallback
import com.example.studentapp.model.api.TspuApi
import kotlin.jvm.Throws

const val SIGN_IN_TAG = "SignInActivity"

class SignInActivity : AppCompatActivity() {

    private lateinit var tspuApi: TspuApi


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        tspuApi = getRetrofitClient().create(TspuApi::class.java)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            onLoginButtonPressed()
        }
    }

    private fun onLoginButtonPressed() {
        val login = findViewById<EditText>(R.id.etLogin).text.toString()
        val password = findViewById<EditText>(R.id.etPassword).text.toString()

        if (isStringValid(login)) {
            if (isStringValid(password)) {
                tspuApi.login(login, password).enqueue(LoginCallback())
                Log.d(SIGN_IN_TAG, "onLoginButtonPressed: ready to login")
                val intent = Intent(this, NavigationActivity::class.java)
                startActivity(intent)
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

    private fun getRetrofitClient(): Retrofit {
        val httpClient = getUnsafeOkHttpClient()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    // Unsafe workaround for HTTPS and API <20
    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }
            })

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory

            // Using logging to see the full api request
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .addInterceptor(logging)
                .hostnameVerifier(object : HostnameVerifier {
                    override fun verify(hostname: String, session: SSLSession): Boolean {
                        return true
                    }
                })
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}