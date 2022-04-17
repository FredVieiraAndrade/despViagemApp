package com.pos.despviagemapp.network

import retrofit2.http.Body
import retrofit2.http.POST

import com.pos.despviagemapp.models.Login
import com.pos.despviagemapp.models.Usuario
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("/api/login")
    suspend fun postLogin(@Body login: Login): Response<Usuario>
}