package com.fundatec.gamecenter.jsonData
import com.google.gson.annotations.SerializedName

data class VendedoresData(
    @SerializedName("cpf")
    val cpf: String
) {
    @SerializedName("_id")
    var id: String? = null
    @SerializedName("notaVendedor")
    var notaVendedor: Double? = null
    @SerializedName("usuario")
    var usuario: Usuario? = null
    constructor() : this( "" )

}

data class Usuario(
    @SerializedName("contato")
    var contato: Contato,
    @SerializedName("email")
    var email: String,
    @SerializedName("foto")
    var foto: String,
    @SerializedName("_id")
    var id: String,
    @SerializedName("nick")
    var nick: String,
    @SerializedName("nomeReal")
    var nomeReal: String,
    @SerializedName("senha")
    var senha: String,
    @SerializedName("vendedor")
    var vendedor: Boolean
)