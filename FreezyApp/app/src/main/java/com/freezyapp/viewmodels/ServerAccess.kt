package com.freezyapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import java.time.LocalDateTime
import java.util.*

class ServerAccess : ViewModel() {
    val BASE_URL = ""
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

    init {
        viewModelScope.launch {

        }
    }
}


interface AuthenticationService {

}

interface CategoryService {
    @POST("/Create/Category")
    fun createCategory(accessToken: UUID, name: String, color: String): Call<ResponseBody> //Måske unødvendigt med return type

    @GET("/AllCategories")
    fun getAllCategories(accessToken: UUID): Call<List<CategoryData>>

    @PUT("/Update/Category")
    fun updateCategory(accessToken: UUID, categoryId: Long, color: String): Call<ResponseBody> //Maybe just like this????

    @DELETE("/Delete/Category")
    fun deleteCategory(accessToken: UUID, categoryId: Long): Call<ResponseBody>
}

interface ItemService {
    @POST("/Create/Item")
    fun createItem(accessToken: UUID, name: String, expirationDate: LocalDateTime, unit: String, storage_Unit_Id: Long, categoryIds: List<Long>): Call<ResponseBody>

    @GET("/Items")
    fun getAllItems(accessToken: UUID, storageId: Long): Call<List<ItemData>>

    @DELETE("/Delete/Item")
    fun deleteItem(accessToken: UUID, itemId: Long): Call<ResponseBody>
}

interface StorageService {
    @POST("/Create/Storage")
    fun createStorage_Unit(accessToken: UUID, name: String): Call<ResponseBody>

    @GET("/AllStorages")
    fun getAllStorages(accessToken: UUID): Call<List<StorageData>>

    @PUT("/Update/Storage")
    fun updateStorage_Unit(accessToken: UUID, storageId: Long/*, updateInfo: ????*/): Call<ResponseBody>

    @DELETE("/Delete/Storage")
    fun deleteStorage_Unit(accessToken: UUID, name: String): Call<ResponseBody>
}