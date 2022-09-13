package com.example.notedatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val key = "name"
        val value = "Serendipity"

       lifecycleScope.launch {

           DataStore.writeString(applicationContext,key,value)
           val value = DataStore.readString(applicationContext,key)
           value.collect {
               Log.d("MY-TAG","value:$it")
           }

       }


    }
}