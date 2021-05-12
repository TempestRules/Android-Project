package com.freezyapp.viewmodels.entities

class Login {

    private lateinit var username: String
    private lateinit var password: String
    private lateinit var accountDetailsName: String

    fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String?) {
        this.username = username!!
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password!!
    }

    fun getAccountDetailsName(): String? {
        return accountDetailsName
    }

    fun setAccountDetailsName(accountDetailsName: String?) {
        this.accountDetailsName = accountDetailsName!!
    }

}