package com.fundatec.gamecenter.jsonData
import com.google.gson.annotations.SerializedName


data class ComunidadesData(
    @SerializedName("nome")
    val nome: String

) {
    @SerializedName("descricao")
    var descricao: String? = null
    @SerializedName("imagem")
    var imagem: String? = null
    @SerializedName("_id")
    var id: String? = null
    constructor() : this ("")

    constructor(nome: String, descricao: String?, imagem: String?) : this(nome) {
        this.descricao = descricao
        this.imagem = imagem
    }
}