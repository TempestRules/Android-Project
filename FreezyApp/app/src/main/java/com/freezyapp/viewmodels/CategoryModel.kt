package com.freezyapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.freezyapp.viewmodels.entities.Category
import com.freezyapp.viewmodels.requestbodies.CategoryData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*

class CategoryModel : ViewModel() {
    val BASE_URL = "http://10.0.2.2:8080"//"http://velm.dk:8080"
    private var retrofit: Retrofit? = null
    var mld = MutableLiveData<List<Category>>()
    var liveList: LiveData<List<Category>> = mld

    fun getClient(): Retrofit {
        if(retrofit == null){
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit!!
    }

    fun createCategory(accessToken: UUID, name: String, color: String){
        val service = getClient().create(CategoryService::class.java)
        var cd = CategoryData()
        cd.setAccessToken(accessToken)
        cd.setColor(color)
        cd.setName(name)
        val call = service.createCategory(cd/*accessToken, name, color*/)
        return call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response != null) {
                    if(response.code() == 201){
                        Log.d("cCatSuccess", "Created new category")
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("cCatError", "Error in creating category: " + t.message)
            }

        })
    }

    fun getAllCategories(accessToken: UUID){
        val service = getClient().create(CategoryService::class.java)
        var cd = CategoryData()
        cd.setAccessToken(accessToken)
        val call = service.getAllCategories(cd)
        return call.enqueue(object: Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if(response != null){
                    if(response.code() == 200){
                        mld.value = response.body()
                        Log.d("gaCat","Successfully read all categories")
                    }
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Log.d("gaCatError", "Error in reading categories: " + t.message)
            }

        })
    }

    fun updateCategory(accessToken: UUID, categoryId: Long, color: String){
        val service = getClient().create(CategoryService::class.java)
        var cd = CategoryData()
        cd.setAccessToken(accessToken)
        cd.setCategoryId(categoryId)
        cd.setColor(color)
        val call = service.updateCategory(cd)
        return call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response != null){
                    if(response.code() == 200){
                        Log.d("uCat","Updated category")
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("uCatError","Error in updating category: " + t.message)
            }

        })
    }

    fun deleteCategory(accessToken: UUID, categoryId: Long){
        val service = getClient().create(CategoryService::class.java)
        var cd = CategoryData()
        cd.setAccessToken(accessToken)
        cd.setCategoryId(categoryId)
        val call = service.deleteCategory(cd)
        return call.enqueue(object: Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response != null){
                    if(response.code() == 200){
                        Log.d("dCat","Deleted category")
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("dCatError","Error in deleting category: " + t.message)
            }

        })
    }
}


interface AuthenticationService {

}

interface CategoryService {
    //@FormUrlEncoded
    @POST("/Create/Category")
    fun createCategory(@Body categoryData: CategoryData/*@Field("accessToken") accessToken: UUID,@Field("name") name: String,@Field("color") color: String*/): Call<Void>

    @GET("/Read/AllCategories")
    fun getAllCategories(@Body categoryData: CategoryData/*accessToken: UUID*/): Call<List<Category>>

    @PUT("/Update/Category")
    fun updateCategory(@Body categoryData: CategoryData/*accessToken: UUID, categoryId: Long, color: String*/): Call<Void> //Maybe just like this????

    @DELETE("/Delete/Category")
    fun deleteCategory(@Body categoryData: CategoryData/*accessToken: UUID, categoryId: Long*/): Call<Void>
}

