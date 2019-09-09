package com.fundatec.gamecenter.jsonData
import com.google.gson.annotations.SerializedName

data class ProdutosData(
    @SerializedName("cmmComprador")
    val cmmComprador: String,
    @SerializedName("descricao")
    val descricao: String,
    @SerializedName("frete")
    val frete: Double,
    @SerializedName("_id")
    val id: String,
    @SerializedName("imagem")
    val imagem: String,
    @SerializedName("nickVendedor")
    val nickVendedor: String,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("notaVenda")
    val notaVenda: Double,
    @SerializedName("valor")
    val valor: Double,
    @SerializedName("vendido")
    val vendido: Boolean
)

data class ProdutoPost(
    @SerializedName("descricao")
    val descricao: String,
    @SerializedName("frete")
    val frete: Double,
    @SerializedName("imagem")
    val imagem: String,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("valor")
    val valor: Double
)

data class ProdutoCompra(
    @SerializedName("cmmComprador")
    val cmmComprador: String,
    @SerializedName("notaVenda")
    val notaVenda: Double
)