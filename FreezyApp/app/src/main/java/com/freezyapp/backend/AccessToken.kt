package com.freezyapp.backend

import android.content.Context
import android.content.SharedPreferences
import com.freezyapp.R

class AccessToken() {

    companion object {
        private lateinit var context: Context;

        fun setContext(context: Context) {
            AccessToken.context = context
        }

        private const val file_key: String = "AccessTokenFile"
        private const val token_key: String = "AccessToken"

        private val pref: SharedPreferences = context.getSharedPreferences(file_key, Context.MODE_PRIVATE)

        fun get(): String? {
            return pref.getString(token_key, null)
        }

        fun set(accessToken: String) {
            with (pref.edit()) {
                putString(token_key, accessToken)
                apply()
            }
        }
    }
}