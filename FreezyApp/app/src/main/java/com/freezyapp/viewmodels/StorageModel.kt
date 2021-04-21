package com.freezyapp.viewmodels

import androidx.lifecycle.ViewModel
import com.freezyapp.viewmodels.requestbodies.StorageData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

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
}

interface StorageService {
    @POST("/Create/Storage")
    fun createStorage_Unit(@Body storageData: StorageData/*accessToken: UUID, name: String*/): Call<ResponseBody>

    @GET("/Read/AllStorages")
    fun getAllStorages(@Body storageData: StorageData/*accessToken: UUID*/): Call<List<StorageData>>

    @PUT("/Update/Storage")
    fun updateStorage_Unit(@Body storageData: StorageData/*accessToken: UUID, storageId: Long, updateInfo: ????*/): Call<ResponseBody>

    @DELETE("/Delete/Storage")
    fun deleteStorage_Unit(@Body storageData: StorageData/*accessToken: UUID, name: String*/): Call<ResponseBody>
}