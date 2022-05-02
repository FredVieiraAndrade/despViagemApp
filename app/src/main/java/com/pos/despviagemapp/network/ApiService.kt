package com.pos.despviagemapp.network

import com.pos.despviagemapp.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("/api/login")
    suspend fun postLogin(@Body login: Login): Response<Usuario>

    @GET("/api/viagens/{matricula}")
    suspend fun getViagens(@Path("matricula") matricula: Int): Response<List<Viagem>>
    //suspend fun getViagens(@Header("Authorization") token: String): Response<List<Viagem>>

    @GET("/api/viagem/{id}")
    suspend fun getDeclaracao(@Path("id") id: Int) : Response<List<Viagem>>

    @POST("/api/viagens")
    suspend fun postDeclaracao(@Body viagem: Viagem) : Response<Resposta>

    @PUT("/api/viagens/{id}")
    suspend fun putDeclaracao(@Path("id") id: Int, @Body viagem: Viagem) : Response<Resposta>

    @DELETE("/api/viagens/{id}")
    suspend fun deleteViagens(@Path("id") id: Int) : Response<Resposta>

    @GET("/api/lancamentos/{id}")
    suspend fun getlancamentos(@Path("id") id: Int) : Response<List<Lancamento>>

    @GET("/api/lancamento/{id}")
    suspend fun getlancamento(@Path("id") id: Int) : Response<Lancamento>

    @POST("/api/lancamentos")
    suspend fun postLancamento(@Body lancamento: Lancamento) : Response<Resposta>

    @DELETE("/api/lancamentos/{id}")
    suspend fun deleteLancamento(@Path("id") id: Int) : Response<Resposta>

    @PUT("/api/apuracao/{id}")
    suspend fun apuracao(@Path("id") id: Int, @Body viagem: Viagem) : Response<Resposta>

}