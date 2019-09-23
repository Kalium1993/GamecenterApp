package com.fundatec.gamecenter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.jsonData.UsuariosData
import com.fundatec.gamecenter.request.GsonJsonRequest
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_login.*

class LoginActivity : AppCompatActivity() {

    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(toolbar)

        queue = Volley.newRequestQueue(baseContext)

        registrar.setOnClickListener {
            registrar()
        }

        logar.setOnClickListener { v ->
//            logar()
            val context =  v.context
            val intent = Intent(context, UsuarioActivity::class.java)
            intent.putExtra("nick", logarNick.text.toString())
            context.startActivity(intent)
        }
    }

    private fun registrar() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/usuario/registrar"
        var usuario = UsuariosData(registrarEmail.text.toString(), registrarNick.text.toString(), registrarSenha.text.toString())
        var post = Gson().toJson(usuario)

        var request = GsonJsonRequest(Request.Method.POST, url, UsuariosData::class.java, post, Response.Listener {
            val intent = Intent(baseContext, UsuarioActivity::class.java)
            intent.putExtra("nick", registrarNick.text.toString())
            startActivity(intent)
        }, Response.ErrorListener { e ->
            Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
        })
        queue?.add(request)
    }

    private fun logar() {
        Toast.makeText(baseContext, "função de logar em desenvolvimento", Toast.LENGTH_SHORT).show()
    }

}
