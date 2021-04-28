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
        access.createCategory("Egon", "Yellow")
    }
}