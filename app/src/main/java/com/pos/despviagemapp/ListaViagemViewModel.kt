package com.pos.despviagemapp

import android.util.Log
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.pos.despviagemapp.adapter.ViagemAdapter
import com.pos.despviagemapp.models.Resposta
import com.pos.despviagemapp.models.Viagem
import com.pos.despviagemapp.network.WebAccess
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ListaViagemViewModel : ViewModel(){

    private val _response = MutableLiveData<List<Viagem>>()
    private val _responseSimples = MutableLiveData<Resposta>()

    val response: LiveData<List<Viagem>>
        get() = _response

    val responseSimples: LiveData<Resposta>
        get() = _responseSimples

    fun get(matricula: Int): Job =
        viewModelScope.launch {
            val response = WebAccess.api.getViagens(matricula)
            if(response.isSuccessful) {
                _response.value  = response.body()
            }
        }

    fun delete(id: Int): Job =
        viewModelScope.launch {
            val response = WebAccess.api.deleteViagens(id)
            if(response.isSuccessful) {
                _responseSimples.value  = response.body()
            }
        }
}