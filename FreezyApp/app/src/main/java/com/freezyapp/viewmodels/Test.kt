package com.freezyapp.viewmodels

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Test : AppCompatActivity() {
    override fun onStart() {
        super.onStart()
        Log.d("asd", "Onstart called")
        main()
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.d("erfs", "Called onCreate")
    }

    fun main(){
        var access = CategoryModel()
        access.createCategory(UUID.fromString("784b1bf7-0b9b-4d6e-a588-bbcece388ad2"/*"425283d7-382f-4bb0-a772-95ddc0f5117d"*/), "Egon", "Yellow")
    }
}