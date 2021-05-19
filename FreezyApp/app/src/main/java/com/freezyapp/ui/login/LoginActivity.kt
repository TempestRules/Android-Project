package com.freezyapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.freezyapp.R
import com.freezyapp.activities.MainActivity
import com.freezyapp.backend.AccessToken

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AccessToken.setContext(applicationContext)
        setContentView(R.layout.activity_login)
        stayedLogin()
    }

    private fun stayedLogin(){
        if (AccessToken.getAutoLogin() == true) {
            if (AccessToken.get() != null) {
                val intent = Intent(this, MainActivity::class.java)
                this?.startActivity(intent)
                finish()
            }
        }



    }
}