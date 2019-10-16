package com.fundatec.gamecenter.jsonData
import com.google.gson.annotations.SerializedName


data class MensagensData(
    @SerializedName("mensagem")
    val mensagem: String
) {
    @SerializedName("_id")
    var id: String? = null
    @SerializedName("topico")
    var topico: String? = null
    @SerializedName("usuario")
    var usuario: Usuario? = null
    constructor() : this("")
}