package com.freezyapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST

class ServerAccess : ViewModel() {

    init {
        viewModelScope.launch {

        }
    }

    fun getAllStorages() {
        val retrofit = Retrofit.Builder().baseUrl("localhostEllerNoget").addConverterFactory(GsonConverterFactory.create()).build()

    }
}

