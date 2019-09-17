package com.fundatec.gamecenter.jsonData
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Constructor

data class UsuariosData(
    @SerializedName("nick")
    val nick: String

) {
    @SerializedName("contato")
    var contato: Contato? = null
    @SerializedName("email")
    var email: String? = null
    @SerializedName("foto")
    var foto: String? = null
    @SerializedName("_id")
    var id: String? = null
    @SerializedName("nomeReal")
    var nomeReal: String? = null
    @SerializedName("senha")
    var senha: String? = null
    @SerializedName("vendedor")
    var vendedor: Boolean? = null
    constructor() : this( "" )

    constructor( email : String, nick: String, senha: String) : this( nick ){
        this.senha = senha
        this.email = email
    }
}

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

data class UsuarioDeletar(
    @SerializedName("_id")
    val id: String
)

data class Contato(
    @SerializedName("cep")
    var cep: String,
    @SerializedName("cidade")
    var cidade: String,
    @SerializedName("estado")
    var estado: String,
    @SerializedName("numero")
    var numero: String,
    @SerializedName("rua")
    var rua: String,
    @SerializedName("telefone")
    var telefone: String
)