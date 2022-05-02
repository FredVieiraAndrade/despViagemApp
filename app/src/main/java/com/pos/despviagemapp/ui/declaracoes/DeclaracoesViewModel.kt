package com.pos.despviagemapp.ui.declaracoes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pos.despviagemapp.models.Lancamento
import com.pos.despviagemapp.models.Resposta
import com.pos.despviagemapp.models.Viagem
import com.pos.despviagemapp.network.WebAccess
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DeclaracoesViewModel : ViewModel() {
    private val _response = MutableLiveData<Lancamento>()
    private val _responseLst = MutableLiveData<List<Lancamento>>()
    private val _declaracao = MutableLiveData<List<Viagem>>()
    private val _declaracaoPost = MutableLiveData<Resposta>()
    private val _declaracaoPut = MutableLiveData<Resposta>()
    private val _lancamentoPost = MutableLiveData<Resposta>()
    private val _lancamentoDelete = MutableLiveData<Resposta>()
    private val _apuracao = MutableLiveData<Resposta>()

    val response: LiveData<Lancamento>
        get() = _response

    val responseLst: LiveData<List<Lancamento>>
        get() = _responseLst

    val declaracao: LiveData<List<Viagem>>
        get() = _declaracao

    val declaracaoPost: LiveData<Resposta>
        get() = _declaracaoPost

    val declaracaoPut: LiveData<Resposta>
        get() = _declaracaoPut

    val lancamentoPost: LiveData<Resposta>
        get() = _lancamentoPost

    val lancamentoDelete: LiveData<Resposta>
        get() = _lancamentoDelete

    val apuracao: LiveData<Resposta>
        get() = _apuracao

    fun getDeclaracao(id : Int): Job =
        viewModelScope.launch {
            val response = WebAccess.api.getDeclaracao(id)
            if(response.isSuccessful) {
                _declaracao.value  = response.body()
            }
        }

    fun postDeclaracao(viagem: Viagem): Job =
        viewModelScope.launch {
            val response = WebAccess.api.postDeclaracao(viagem)
            if(response.isSuccessful) {
                _declaracaoPost.value  = response.body()
            }
        }

    fun putDeclaracao(id: Int, viagem: Viagem): Job =
        viewModelScope.launch {
            val response = WebAccess.api.putDeclaracao(id, viagem)
            if(response.isSuccessful) {
                _declaracaoPut.value  = response.body()
            }
        }

    fun get(id : Int): Job =
        viewModelScope.launch {
            val response = WebAccess.api.getlancamento(id)
            if(response.isSuccessful) {
                _response.value  = response.body()
            }
        }

    fun getLancamentos(id : Int): Job =
        viewModelScope.launch {
            val response = WebAccess.api.getlancamentos(id)
            if(response.isSuccessful) {
                _responseLst.value  = response.body()
            }
        }

    fun postLancamento(lancamento: Lancamento) : Job =
        viewModelScope.launch {
            val response = WebAccess.api.postLancamento(lancamento)
            if(response.isSuccessful) {
                _lancamentoPost.value = response.body()
            }
        }

    fun deleteLancamento(id : Int): Job =
        viewModelScope.launch {
            val response = WebAccess.api.deleteLancamento(id)
            _lancamentoDelete.value = response.body()
        }

    fun apuracao(id: Int, viagem: Viagem): Job =
        viewModelScope.launch {
            val response = WebAccess.api.apuracao(id, viagem)
            if(response.isSuccessful) {
                _apuracao.value = response.body()
            }
        }
}