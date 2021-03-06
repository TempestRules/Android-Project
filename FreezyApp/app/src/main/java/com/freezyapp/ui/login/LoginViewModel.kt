package com.example.login

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freezyapp.R
import com.freezyapp.backend.AccessToken
import com.freezyapp.viewmodels.entities.Login
import com.freezyapp.viewmodels.requestbodies.LoginData
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

class LoginViewModel : ViewModel() {

    val BASE_URL = "http://velm.dk:8080"
    private var retrofit: Retrofit? = null
    var mld = MutableLiveData<UUID>()
    var liveData: LiveData<UUID> = mld

    var username: String? = null
    var password: String? = null
    var password2: String? = null
    var stay_login: Boolean = false
    var name: String? = "enter name"
    var corretUser = false

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
            var id = Login()
            id.setUsername(username)
            id.setPassword(password)
            id.setAccountDetailsName(name)

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
            Toast.makeText(AccessToken.getContext(), R.string.not_same_password, Toast.LENGTH_SHORT).show()
        }
    }

    fun login(){
        val service = getClient().create(LoginService::class.java)
        var id = Login()
        id.setUsername(username)
        id.setPassword(password)



        val call = service.login(id)
        return call.enqueue(object: Callback<LoginData> {
            override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
                if(response != null){
                    if(response.code() == 200){
                        mld.value = response.body()?.getauthenticationToken()
                        AccessToken.set(response.body()?.getauthenticationToken()!!)
                        corretUser = true
                        Log.d("login", "login successfully")
                        Log.d("stat", stay_login.toString())
                        if (stay_login) {
                            AccessToken.setAutoLogin(stay_login)
                        }

                    }
                }
            }

            override fun onFailure(call: Call<LoginData>, t: Throwable) {
                Log.d("loginError", "Error in login: " + t.message)
                corretUser = false
            }


        })

    }

    interface LoginService {
        @POST("/Create/Signup")
        fun createAccount(@Body id: Login): Call<Void>

        @POST("/Read/Authenticate")
        fun login(@Body id: Login): Call<LoginData>
    }

}