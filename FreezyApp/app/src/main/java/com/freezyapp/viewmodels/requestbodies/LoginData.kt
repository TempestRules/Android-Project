package com.freezyapp.viewmodels.requestbodies

import java.util.*

class LoginData {
    private var authenticationToken: UUID? = null

    fun getauthenticationToken(): UUID? {
        return authenticationToken
    }

    fun setauthenticationToken(authenticationToken: UUID?) {
        this.authenticationToken = authenticationToken!!
    }
}