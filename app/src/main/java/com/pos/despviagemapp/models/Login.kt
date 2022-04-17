package com.pos.despviagemapp.models

import com.squareup.moshi.Json


data class Login(
    @Json(name="login")
    val login: String?,
    @Json(name="senha")
    val senha: String?
)
