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

    constructor( contato: Contato, email: String, foto: String?, nick: String, nomeReal: String?, senha: String) : this( nick ){
        this.contato = contato
        this.foto = foto
        this.nomeReal = nomeReal
        this.senha = senha
        this.email = email
    }
}

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