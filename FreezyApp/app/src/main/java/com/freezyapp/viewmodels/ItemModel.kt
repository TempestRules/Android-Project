package com.freezyapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freezyapp.backend.AccessToken
import com.freezyapp.viewmodels.entities.Item
import com.freezyapp.viewmodels.entities.ItemModelBools
import com.freezyapp.viewmodels.requestbodies.ItemData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ItemModel: ViewModel() {
    val BASE_URL = "http://velm.dk:8080"
    private var retrofit: Retrofit? = null
    var mld = MutableLiveData<List<Item>>()
    var liveList: LiveData<List<Item>> = mld
    private var currentItem: Item? = null
    private val imb = ItemModelBools()

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

    fun setCurrentItem(item: Item?){
        currentItem = item
    }

    fun resetItemList() {
        mld.value = emptyList()
    }

    fun createItem(name: String, expirationDate: LocalDateTime?, unit: String, storage_Unit_Id: Long, quantity: Double, categoryIds: List<Long>){
        val service = getClient().create(ItemService::class.java)
        var id = ItemData()
        id.setAccessToken(AccessToken.get())
        id.setName(name)
        id.setQuantity(quantity)
        id.setExpirationDate(expirationDate?.format(DateTimeFormatter.ISO_DATE_TIME))
        id.setUnit(unit)
        id.setStorage_Unit_Id(storage_Unit_Id)
        id.setCategoryIds(categoryIds)
        val call = service.createItem(id)
        return call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response != null){
                    if(response.code() == 201){
                        Log.d("cItem", "Created item successfully")
                        getItems()
                    }
                    else {
                        Log.d("cItemErrorCode", "Got error code from server: " + response.code().toString())
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("cItemError", "Error in creating item: " + t.message)
            }
        })
    }

    fun getItems(){
        val service = getClient().create(ItemService::class.java)
        var id = ItemData()
        id.setAccessToken(AccessToken.get())
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
                        getItems()
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("dItemError", "Error in deleting item: " + t.message)
            }

        })
    }

    fun updateItem(updateInfo: Item){
        val service = getClient().create(ItemService::class.java)
        var id = ItemData()
        id.setAccessToken(AccessToken.get())
        id.setQuantity(updateInfo.getQuantity())
        id.setItemId(updateInfo.getId())
        id.setStorage_Unit_Id(updateInfo.getStorageUnitId())
        id.setCategoryIds(updateInfo.getCategoryIds())
        id.setUnit(updateInfo.getUnit())
        id.setExpirationDate(updateInfo.getExpiration_date())
        id.setName(updateInfo.getName())
        val call = service.updateItem(id)
        return call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response != null){
                    if(response.code() == 200) {
                        Log.d("uItem", "Updated item successfully")
                        getItems()
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("uItemError", "Error in updating item: " + t.message)
            }
        })
    }

    fun setSortCategory(sortCategory: Boolean){
        imb.setSortCategory(sortCategory)
    }

    fun getSortCategory(): Boolean {
        return imb.getSortCategory()
    }

    fun setOnlyExpired(onlyExpired: Boolean){
        imb.setOnlyExpired(onlyExpired)
    }

    fun getOnlyExpired(): Boolean {
        return imb.getOnlyExpired()
    }

    fun setSortStorage(sortStorage: Boolean){
        imb.setSortStorage(sortStorage)
    }

    fun getSortStorage(): Boolean {
        return imb.getSortStorage()
    }
}

interface ItemService {
    @POST("/Create/Item")
    fun createItem(@Body id: ItemData/*accessToken: UUID, name: String, expirationDate: LocalDateTime, unit: String, storage_Unit_Id: Long, categoryIds: List<Long>*/): Call<Void>

    @POST("/Read/Items")
    fun getAllItems(@Body id: ItemData/*accessToken: UUID, storageId: Long*/): Call<List<Item>>

    @POST("/Delete/Item")
    fun deleteItem(@Body id: ItemData/*accessToken: UUID, itemId: Long*/): Call<Void>

    @PUT("/Update/Item")
    fun updateItem(@Body id: ItemData): Call<Void>
}