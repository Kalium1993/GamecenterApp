package com.fundatec.gamecenter.jsonData
import com.google.gson.annotations.SerializedName

data class UsuariosData(
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

data class UsuarioRegistrar(
    @SerializedName("email")
    val email: String,
    @SerializedName("nick")
    val nick: String,
    @SerializedName("senha")
    val senha: String
)

data class UsuarioEditar(
    @SerializedName("contato")
    val contato: Contato,
    @SerializedName("email")
    val email: String,
    @SerializedName("foto")
    val foto: String?,
    @SerializedName("nick")
    val nick: String,
    @SerializedName("nomeReal")
    val nomeReal: String?,
    @SerializedName("senha")
    val senha: String
)

data class Contato(
    @SerializedName("cep")
    val cep: String,
    @SerializedName("cidade")
    val cidade: String,
    @SerializedName("estado")
    val estado: String,
    @SerializedName("numero")
    val numero: String,
    @SerializedName("rua")
    val rua: String,
    @SerializedName("telefone")
    val telefone: String
)