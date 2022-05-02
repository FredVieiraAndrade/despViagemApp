package com.pos.despviagemapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pos.despviagemapp.models.Login
import com.pos.despviagemapp.models.Usuario
import com.pos.despviagemapp.network.WebAccess
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {

    private val _response = MutableLiveData<Usuario>()
    val response: LiveData<Usuario>
        get() = _response

    fun post(login: Login): Job =
        viewModelScope.launch {
            val response = WebAccess.api.postLogin(login)

            if(response.isSuccessful) {
                _response.value = response.body()
            }
        }
}