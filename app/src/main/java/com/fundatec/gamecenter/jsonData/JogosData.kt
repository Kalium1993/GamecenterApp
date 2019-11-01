package com.fundatec.gamecenter.jsonData
import com.google.gson.annotations.SerializedName


data class JogosData(
    @SerializedName("notaUsuario")
    val notaUsuario: Double
) {
    @SerializedName("foto")
    var foto: String? = null
    @SerializedName("_id")
    var id: String? = null
    @SerializedName("notaMidia")
    var notaMidia: Double? = null
    @SerializedName("titulo")
    var titulo: String? = null
    @SerializedName("notaUsuarios")
    var notaUsuarios: Double? = null
    @SerializedName("notaUsuariosTotal")
    var notaUsuariosTotal: Double? = null
    @SerializedName("notas")
    var notas: Double? = null
}