package com.fundatec.gamecenter.jsonData
import com.google.gson.annotations.SerializedName


data class VendedoresData(
    @SerializedName("cpf")
    val cpf: String,
    @SerializedName("foto")
    val foto: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("nick")
    val nick: String,
    @SerializedName("nomeReal")
    val nomeReal: String,
    @SerializedName("notaVendedor")
    val notaVendedor: Double
)