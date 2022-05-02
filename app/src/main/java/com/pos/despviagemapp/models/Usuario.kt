package com.pos.despviagemapp.models

import com.squareup.moshi.Json

data class Usuario(
    @Json(name="id")
    val id: Int?,
    @Json(name="login")
    val login: String?,
    @Json(name="nome")
    val nome: String?,
    @Json(name="email")
    val email: String?,
    @Json(name="matricula")
    val matricula: Int?,
    @Json(name="banco")
    val banco: String?,
    @Json(name="agencia")
    val agencia: String?,
    @Json(name="conta_corrente")
    val conta_corrente: String?,
    @Json(name="token")
    val token: String?
)
