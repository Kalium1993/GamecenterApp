package com.fundatec.gamecenter.jsonData
import com.google.gson.annotations.SerializedName

data class ProdutosData(
    @SerializedName("nome")
    val nome: String

) {
    @SerializedName("cmmComprador")
    var cmmComprador: String? = null
    @SerializedName("descricao")
    var descricao: String? = null
    @SerializedName("frete")
    var frete: Double? = null
    @SerializedName("_id")
    var id: String? = null
    @SerializedName("imagem")
    var imagem: String? = null
    @SerializedName("nickVendedor")
    var nickVendedor: String? = null
    @SerializedName("notaVenda")
    var notaVenda: Double? = null
    @SerializedName("valor")
    var valor: Double? = null
    @SerializedName("vendido")
    var vendido: Boolean? = null
    constructor() : this( "" )

    constructor(descricao: String, frete: Double, imagem: String?, nome: String, valor: Double) : this(nome) {
        this.descricao = descricao
        this.frete = frete
        this.imagem = imagem
        this.valor = valor
    }

    constructor(cmmComprador: String, notaVenda: Double) : this ("") {
        this.cmmComprador = cmmComprador
        this.notaVenda = notaVenda
    }
}