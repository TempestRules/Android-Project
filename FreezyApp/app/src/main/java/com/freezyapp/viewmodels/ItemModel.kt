package com.freezyapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freezyapp.backend.AccessToken
import com.freezyapp.viewmodels.entities.Item
import com.freezyapp.viewmodels.requestbodies.ItemData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
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
    private var currentItem: Item? = null

    fun getClient(): Retrofit {
        if(retrofit == null){
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit!!
    }

    fun getCurrentItem(): Item? {
        return currentItem
    }

    fun setCurrentItem(item: Item){
        currentItem = item
    }

    fun createItem(name: String, expirationDate: LocalDateTime, unit: String, storage_Unit_Id: Long, quantity: Double, categoryIds: List<Long>){
        val service = getClient().create(ItemService::class.java)
        var id = ItemData()
        id.setAccessToken(AccessToken.get())
        id.setName(name)
        id.setQuantity(quantity)
        id.setExpirationDate(expirationDate)
        id.setUnit(unit)
        id.setStorage_Unit_Id(storage_Unit_Id)
        id.setCategoryIds(categoryIds)
        val call = service.createItem(id)
        return call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response != null){
                    if(response.code() == 201){
                        Log.d("cItem", "Created item successfully")
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("cItemError", "Error in creating item: " + t.message)
            }
        })
    }

    fun getItems(storageId: Long){
        val service = getClient().create(ItemService::class.java)
        var id = ItemData()
        id.setAccessToken(AccessToken.get())
        id.setStorage_Unit_Id(storageId)
        val call = service.getAllItems(id)
        return call.enqueue(object: Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if(response != null){
                    if(response.code() == 200){
                        mld.value = response.body()
                        Log.d("gaItem", "Got all items successfully")
                    }
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                Log.d("gaItemError", "Error in getting all items: " + t.message)
            }
        })
    }

    fun deleteItem(itemId: Long){
        val service = getClient().create(ItemService::class.java)
        var id = ItemData()
        id.setAccessToken(AccessToken.get())
        id.setItemId(itemId)
        val call = service.deleteItem(id)
        return call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response != null){
                    if(response.code() == 200){
                        Log.d("dItem", "Deleted item successfully")
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("dItemError", "Error in deleting item: " + t.message)
            }

        })
    }
}

interface ItemService {
    @POST("/Create/Item")
    fun createItem(@Body id: ItemData/*accessToken: UUID, name: String, expirationDate: LocalDateTime, unit: String, storage_Unit_Id: Long, categoryIds: List<Long>*/): Call<Void>

    @GET("/Read/Items")
    fun getAllItems(@Body id: ItemData/*accessToken: UUID, storageId: Long*/): Call<List<Item>>

    @DELETE("/Delete/Item")
    fun deleteItem(@Body id: ItemData/*accessToken: UUID, itemId: Long*/): Call<Void>
}