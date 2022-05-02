package com.pos.despviagemapp.models

import com.squareup.moshi.Json

data class Resposta(
    @Json(name="message")
    val mensagem: String?,
    @Json(name="id")
    val id: Int?,
)
