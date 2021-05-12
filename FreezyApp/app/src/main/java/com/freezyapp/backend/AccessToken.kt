package com.freezyapp.backend

import android.content.Context
import android.content.SharedPreferences
import com.freezyapp.R
import java.lang.Error
import java.util.*

class AccessToken() {

    companion object {
        private lateinit var context: Context;

        fun setContext(context: Context) {
            AccessToken.context = context
            pref = Companion.context.getSharedPreferences(file_key, Context.MODE_PRIVATE)
        }

        private const val file_key: String = "AccessTokenFile"
        private const val token_key: String = "AccessToken"

        private var pref: SharedPreferences? = null

        fun get(): UUID? {
            if (pref == null)
                return null

            val storedValue = pref!!.getString(token_key, null)
            return if (storedValue == null) {
                null
            } else {
                UUID.fromString(storedValue)
            }
        }

        fun set(accessToken: UUID) {
            if (pref == null) {
                throw Error("Context have not been sat!");
            }

            with (pref!!.edit()) {
                putString(token_key, accessToken.toString())
                apply()
            }
        }
    }
}