package com.freezyapp.viewmodels.requestbodies

class LoginData {
    private var username: String? = null
    private var password: String? = null
    private var name: String? = null

    fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String?) {
        this.username = name
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = name
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }
}