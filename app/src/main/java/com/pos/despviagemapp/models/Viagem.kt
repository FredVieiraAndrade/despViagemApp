package com.pos.despviagemapp.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*


data class Viagem(
    @Json(name="viagem_id")
    val ViagemID: Int?,
    @Json(name="data_inicio")
    val DataInicio: String?,
    @Json(name="data_fim")
    val DataFim: String?,
    @Json(name="chamado")
    val Chamado: String?,
    @Json(name="justificativa")
    val Justificativa: String?,
    @Json(name="motivo")
    val Motivo: Int?,
    @Json(name="adiantamento")
    val Adiantamento: Double?,
    @Json(name="debito_credito")
    val DebitoCredito: Double?,
    @Json(name="cento_custo")
    val CentroCusto: Int?,
    @Json(name="situacao")
    val Situacao: Int?,
    @Json(name="matricula")
    val Matricula: Int?,
)