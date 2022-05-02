package com.pos.despviagemapp.models

import com.squareup.moshi.Json

data class Lancamento(
    @Json(name="lancamento_id")
    val LancamentoID: Int?,
    @Json(name="viagem_id")
    val ViagemID: Int?,
    @Json(name="tipo_despesa_id")
    val TipoDespesaID: Int?,
    @Json(name="valor_despesa")
    val ValorDespesa: Double?,
    @Json(name="observacao")
    val Observacao: String?
)