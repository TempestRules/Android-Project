package com.freezyapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freezyapp.viewmodels.entities.Item
import com.freezyapp.viewmodels.entities.Storage_Unit
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import java.time.LocalDateTime
import java.util.*

class ItemModel: ViewModel() {
    val BASE_URL = "http://10.0.2.2:8080"
    private var retrofit: Retrofit? = null
    var mld = MutableLiveData<List<Item>>()
    var liveList: LiveData<List<Item>> = mld


}

interface ItemService {
    @POST("/Create/Item")
    fun createItem(accessToken: UUID, name: String, expirationDate: LocalDateTime, unit: String, storage_Unit_Id: Long, categoryIds: List<Long>): Call<Void>

    @GET("/Read/Items")
    fun getAllItems(accessToken: UUID, storageId: Long): Call<List<Item>>

    @DELETE("/Delete/Item")
    fun deleteItem(accessToken: UUID, itemId: Long): Call<Void>
}