package com.freezyapp.viewmodels

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freezyapp.viewmodels.entities.Category
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
    var mld = MutableLiveData<List<Storage_Unit>>()
    var liveList: LiveData<List<Storage_Unit>> = mld
    private var currentStorage_Unit: Storage_Unit? = null

    fun getCurrentStorageUnit(): Storage_Unit? {
        return currentStorage_Unit
    }

    fun setCurrentStorageUnit(storageUnit: Storage_Unit?){
        currentStorage_Unit = storageUnit
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

    fun createStorage(accessToken: UUID, name: String, color: String){
        val service = getClient().create(StorageService::class.java)
        var sd = StorageData()
        sd.setAccessToken(accessToken)
        sd.setName(name)
        sd.setColor(color)
        val call = service.createStorage_Unit(sd)
        return call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response != null){
                    if(response.code() == 201){
                        Log.d("cSto","Updated storage unit")
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("cStoError","Error in updating storage unit: " + t.message)
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
                        mld.value = response.body()
                        Log.d("gaCat","Successfully read all storage units")
                    }
                }
            }

            override fun onFailure(call: Call<List<Storage_Unit>>, t: Throwable) {
                Log.d("gaStoError", "Error in getting all storage units: " + t.message)
            }

        })
    }

    fun updateStorage(accessToken: UUID, updateInfo: Storage_Unit){
        val service = getClient().create(StorageService::class.java)
        var sd = StorageData()
        sd.setAccessToken(accessToken)
        sd.setStorageId(updateInfo.getId())
        sd.setUpdateInfo(updateInfo)
        val call = service.updateStorage_Unit(sd)
        return call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response != null){
                    if(response.code() == 200){
                        Log.d("uSto", "Updated storage unit")
                    }
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("uSto","Error in updating storage unit: " + t.message)
            }
        })
    }

    fun deleteStorage(accessToken: UUID, storageId: Long){
        val service = getClient().create(StorageService::class.java)
        var sd = StorageData()
        sd.setAccessToken(accessToken)
        sd.setStorageId(storageId)
        val call = service.deleteStorage_Unit(sd)
        return call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response != null){
                    if(response.code() == 200){
                        Log.d("dSto", "Deleted storage unit")
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("dStoError","Error in deleting storage unit: " + t.message)
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
    fun updateStorage_Unit(@Body storageData: StorageData/*accessToken: UUID, storageId: Long, updateInfo: ????*/): Call<Void>

    @DELETE("/Delete/Storage")
    fun deleteStorage_Unit(@Body storageData: StorageData/*accessToken: UUID, name: String*/): Call<Void>
}