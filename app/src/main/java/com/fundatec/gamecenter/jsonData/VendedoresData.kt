package com.fundatec.gamecenter.jsonData
import com.google.gson.annotations.SerializedName

data class VendedoresData(
    @SerializedName("cpf")
    val cpf: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("notaVendedor")
    val notaVendedor: Double,
    @SerializedName("usuario")
    val usuario: Usuario
)

data class VendedorAtivar(
    @SerializedName("cpf")
    val cpf: String
)

data class Usuario(
    @SerializedName("contato")
    val contato: Contato,
    @SerializedName("email")
    val email: String,
    @SerializedName("foto")
    val foto: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("nick")
    val nick: String,
    @SerializedName("nomeReal")
    val nomeReal: String,
    @SerializedName("senha")
    val senha: String,
    @SerializedName("vendedor")
    val vendedor: Boolean
)