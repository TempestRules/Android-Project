package com.freezyapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.freezyapp.viewmodels.entities.Storage_Unit
import com.freezyapp.viewmodels.requestbodies.StorageData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*

class StorageModel : ViewModel() {
    val BASE_URL = "http://10.0.2.2:8080"
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if(retrofit == null){
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit!!
    }

    fun createStorage(accessToken: UUID, name: String){
        val service = getClient().create(StorageService::class.java)
        var sd = StorageData()
        sd.setAccessToken(accessToken)
        sd.setName(name)
        val call = service.createStorage_Unit(sd)
        return call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response != null){
                    if(response.code() == 201){
                        Log.d("uSto","Updated storage unit")
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("uStoError","Error in updating storage unit: " + t.message)
            }

        })
    }

    fun getAllStorages(accessToken: UUID){
        val service = getClient().create(StorageService::class.java)
        var sd = StorageData()
        sd.setAccessToken(accessToken)
        val call = service.getAllStorages(sd)
        return call.enqueue(object: Callback<List<Storage_Unit>> {
            override fun onResponse(call: Call<List<Storage_Unit>>, response: Response<List<Storage_Unit>>) {
                if(response != null){
                    if(response.code() == 200) {

                    }
                }
            }

            override fun onFailure(call: Call<List<Storage_Unit>>, t: Throwable) {
                Log.d("")
            }

        })
    }
}

interface StorageService {
    @POST("/Create/Storage")
    fun createStorage_Unit(@Body storageData: StorageData/*accessToken: UUID, name: String*/): Call<Void>

    @GET("/Read/AllStorages")
    fun getAllStorages(@Body storageData: StorageData/*accessToken: UUID*/): Call<List<Storage_Unit>>

    @PUT("/Update/Storage")
    fun updateStorage_Unit(@Body storageData: StorageData/*accessToken: UUID, storageId: Long, updateInfo: ????*/): Call<ResponseBody>

    @DELETE("/Delete/Storage")
    fun deleteStorage_Unit(@Body storageData: StorageData/*accessToken: UUID, name: String*/): Call<ResponseBody>
}