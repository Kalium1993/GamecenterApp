package com.fundatec.gamecenter.shared

import android.content.Context

class Logado(context: Context) {
    val LOGADO = "Logado"
    val LOGADO_ID = "idLogado"
    val LOGADO_NICK = "nickLogado"

    val logado = context.getSharedPreferences(LOGADO, Context.MODE_PRIVATE)

    fun getLogadoId() : String{
        return logado.getString(LOGADO_ID, "")
    }

    fun setLogadoId(id: String){
        val editor = logado.edit()
        editor.putString(LOGADO_ID, id)
        editor.apply()
    }

    fun getLogadoNick() : String{
        return logado.getString(LOGADO_NICK, "")
    }

    fun setLogadoNick(nick: String){
        val editor = logado.edit()
        editor.putString(LOGADO_NICK, nick)
        editor.apply()
    }
}