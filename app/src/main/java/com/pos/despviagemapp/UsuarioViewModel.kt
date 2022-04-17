package com.pos.despviagemapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pos.despviagemapp.models.Login
import com.pos.despviagemapp.models.Usuario
import com.pos.despviagemapp.network.ApiService
import com.pos.despviagemapp.network.WebAccess
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*

enum class RestApiStatus {
    LOADING, ERROR, DONE
}

class UsuarioViewModel : ViewModel() {

    // Reference to the RecyclerView adapter
    //private lateinit var adapter: Recl

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun post(login: Login): Job =
        viewModelScope.launch {
            val response = WebAccess.api.postLogin(login)
            Log.i("MEUAPP","Response: ${response.isSuccessful}")

            if(response.isSuccessful){
                val usuario = response.body()
                Log.i("MEUAPP","Response: ${usuario}")
            }
        }

}