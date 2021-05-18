package com.example.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freezyapp.backend.AccessToken
import com.freezyapp.data.DataBaseHandler
import com.freezyapp.data.model.User
import com.freezyapp.viewmodels.entities.Login
import com.freezyapp.viewmodels.requestbodies.LoginData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

class LoginViewModel : ViewModel() {

    val BASE_URL = "http://10.0.2.2:8080"
    private var retrofit: Retrofit? = null
    var mld = MutableLiveData<UUID>()

    var username: String? = null
    var password: String? = null
    var password2: String? = null
    var stay_login: Boolean = false
    var name: String? = "enter name"

    init{
        Log.i("RegistrationViewModel", "RegistrationViewModel created")
    }

    fun getClient(): Retrofit {
        if(retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("RegistrationViewModel", "RegistrationViewModel destroyed")
    }

    fun createAccount(){
        if (password == password2) {
            val service = getClient().create(LoginService::class.java)
            var id = LoginData()
            id.setUsername(username)
            id.setPassword(password)
            id.setName(name)

            val call = service.createAccount(id)
            return call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response != null) {
                        if (response.code() == 201) {
                            Log.d("cAccount", "Created account successfully")
                        }
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("cAccountError", "Error in creating account: " + t.message)
                }
            })
        } else {

        }
    }

    fun login(){
        val service = getClient().create(LoginService::class.java)
        var id = LoginData()
        id.setUsername(username)
        id.setPassword(password)


        val call = service.login(id)
        return call.enqueue(object: Callback<UUID> {
            override fun onResponse(call: Call<UUID>, response: Response<UUID>) {
                if(response != null){
                    if(response.code() == 200){
                        mld.value = response.body()
                        Log.d("login", "login successfully")


                    }
                }
            }

            override fun onFailure(call: Call<UUID>, t: Throwable) {
                Log.d("loginError", "Error in login: " + t.message)
            }


        })

    }

    interface LoginService {
        @POST("/Create/Signup")
        fun createAccount(@Body id: LoginData): Call<Void>

        @POST("/Read/Authenticate")
        fun login(@Body id: LoginData): Call<UUID>
    }

}