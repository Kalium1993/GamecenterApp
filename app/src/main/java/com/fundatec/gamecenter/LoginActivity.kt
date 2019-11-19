package com.fundatec.gamecenter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.jsonData.UsuariosData
import com.fundatec.gamecenter.request.GsonJsonClassRequest
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
            logar()
        }

        msgRegistrar.setOnClickListener {
            registrar.visibility = View.VISIBLE
            registrarNick.visibility = View.VISIBLE
            registrarEmail.visibility = View.VISIBLE
            registrarSenha.visibility = View.VISIBLE

            logar.visibility = View.GONE
            logarNick.visibility = View.GONE
            logarSenha.visibility = View.GONE
        }

        msgLogar.setOnClickListener {
            registrar.visibility = View.GONE
            registrarNick.visibility = View.GONE
            registrarEmail.visibility = View.GONE
            registrarSenha.visibility = View.GONE

            logar.visibility = View.VISIBLE
            logarNick.visibility = View.VISIBLE
            logarSenha.visibility = View.VISIBLE
        }
    }

    private fun registrar() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/usuario/registrar"
        var usuario = UsuariosData(registrarEmail.text.toString(), registrarNick.text.toString(), registrarSenha.text.toString())
        var post = Gson().toJson(usuario)

        if (registrarEmail.text.toString().isEmpty() || registrarNick.text.toString().isEmpty() || registrarSenha.text.toString().isEmpty()) {
            Toast.makeText( baseContext, "Todos os campos devem ser preenchidos", Toast.LENGTH_LONG).show()
        } else if (!registrarEmail.text.toString().contains("@")) {
            Toast.makeText( baseContext, "Email inválido", Toast.LENGTH_LONG).show()
        } else {
            var request = GsonJsonRequest(Request.Method.POST, url, UsuariosData::class.java, post, Response.Listener {
                val intent = Intent(baseContext, UsuarioActivity::class.java)
                intent.putExtra("nick", registrarNick.text.toString())
                intent.putExtra("senha", registrarSenha.text.toString())
                startActivity(intent)
            }, Response.ErrorListener { e ->
                Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
            })
            queue?.add(request)
        }
    }

    private fun logar() {
        var nick = logarNick.text.toString()

        var senha = if(logarSenha.text.toString().isEmpty())
            null
        else
            logarSenha.text.toString()

        val intent = Intent(baseContext, UsuarioActivity::class.java)
        intent.putExtra("nick", nick)
        intent.putExtra("senha", senha)
        startActivity(intent)
    }

}
