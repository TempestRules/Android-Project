package com.freezyapp.backend

import android.content.Context
import android.content.SharedPreferences
import com.freezyapp.R

class AccessToken(val activity: Context) {

    private val file_key: String = activity.getString(R.string.preference_file_key)
    private val token_key: String = activity.getString(R.string.preference_access_token_key)

    private val pref: SharedPreferences = activity.getSharedPreferences(file_key, Context.MODE_PRIVATE)

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