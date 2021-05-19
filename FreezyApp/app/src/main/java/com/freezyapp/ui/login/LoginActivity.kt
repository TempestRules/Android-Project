package com.freezyapp.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.freezyapp.R
import com.freezyapp.backend.AccessToken

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        AccessToken.setContext(applicationContext)
    }
}