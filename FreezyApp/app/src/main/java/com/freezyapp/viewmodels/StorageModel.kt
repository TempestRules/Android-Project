package com.freezyapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freezyapp.backend.AccessToken
import com.freezyapp.viewmodels.entities.Storage_Unit
import com.freezyapp.viewmodels.requestbodies.StorageData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*

class StorageModel : ViewModel() {
    val BASE_URL = "http://velm.dk:8080"
    private var retrofit: Retrofit? = null
    var mld = MutableLiveData<List<Storage_Unit>>()
    var liveList: LiveData<List<Storage_Unit>> = mld
    private var currentStorage_Unit: Storage_Unit? = null

    fun getCurrentStorageUnit(): Storage_Unit? {
        return currentStorage_Unit
    }

    fun setCurrentStorageUnit(storageUnit: Storage_Unit?) {
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

    fun createStorage(name: String, color: String){
        val service = getClient().create(StorageService::class.java)
        var sd = StorageData()
        sd.setAccessToken(AccessToken.get())
        sd.setName(name)
        sd.setColor(color)
        val call = service.createStorage_Unit(sd)
        call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response != null){
                    if(response.code() == 201){
                        Log.d("cSto","Updated storage unit")
                        getAllStorages()
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("cStoError","Error in updating storage unit: " + t.message)
            }

        })
    }

    fun getAllStorages(){
        val service = getClient().create(StorageService::class.java)
        var sd = StorageData()
        sd.setAccessToken(AccessToken.get())
        val call = service.getAllStorages(sd)
        return call.enqueue(object: Callback<List<Storage_Unit>> {
            override fun onResponse(call: Call<List<Storage_Unit>>, response: Response<List<Storage_Unit>>) {
                if(response != null){
                    if(response.code() == 200) {
                        mld.value = response.body()
                        Log.d("gaSto","Successfully read all storage units")
                    }
                    else {
                        Log.d("gaStoErrorCode", "Got error code from server: " + response.code().toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<Storage_Unit>>, t: Throwable) {
                Log.d("gaStoError", "Error in getting all storage units: " + t.message)
            }

        })
    }

    fun updateStorage(updateInfo: Storage_Unit){
        val service = getClient().create(StorageService::class.java)
        var sd = StorageData()
        sd.setAccessToken(AccessToken.get())
        sd.setStorageId(updateInfo.getId())
        sd.setName(updateInfo.getName())
        sd.setColor(updateInfo.getColor())
        val call = service.updateStorage_Unit(sd)
        return call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response != null){
                    if(response.code() == 200){
                        Log.d("uSto", "Updated storage unit")
                        getAllStorages()
                    }
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("uSto","Error in updating storage unit: " + t.message)
            }
        })
    }

    fun deleteStorage(storageId: Long){
        val service = getClient().create(StorageService::class.java)
        var sd = StorageData()
        sd.setAccessToken(AccessToken.get())
        sd.setStorageId(storageId)
        val call = service.deleteStorage_Unit(sd)
        return call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response != null){
                    if(response.code() == 200){
                        Log.d("dSto", "Deleted storage unit")
                        getAllStorages()
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

    @POST("/Read/AllStorages")
    fun getAllStorages(@Body storageData: StorageData/*accessToken: UUID*/): Call<List<Storage_Unit>>

    @PUT("/Update/Storage")
    fun updateStorage_Unit(@Body storageData: StorageData/*accessToken: UUID, storageId: Long, updateInfo: ????*/): Call<Void>

    @POST("/Delete/Storage")
    fun deleteStorage_Unit(@Body storageData: StorageData/*accessToken: UUID, name: String*/): Call<Void>
}