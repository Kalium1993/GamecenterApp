package com.fundatec.gamecenter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.jsonData.UsuariosData
import com.fundatec.gamecenter.jsonData.VendedorAtivar
import com.fundatec.gamecenter.request.GsonJsonClassRequest
import com.fundatec.gamecenter.request.GsonJsonRequest
import com.google.gson.Gson
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_usuario.*
import kotlinx.android.synthetic.main.content_usuario.*

class UsuarioActivity : AppCompatActivity() {

    private var nick: String = ""
    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)
        setSupportActionBar(toolbar)

        nick = intent.getStringExtra("nick")
        queue = Volley.newRequestQueue(baseContext)
        readUsuario()

        ShowAtivarVendedor.setOnClickListener {
            findViewById<TextView>(R.id.msgCPF).visibility = View.VISIBLE
            findViewById<EditText>(R.id.cpfUsuario).visibility = View.VISIBLE
            findViewById<Button>(R.id.AtivarVendedor).visibility = View.VISIBLE

            editarUsuario.setOnClickListener { v ->
                ativarPerfilVendedor()
                val context =  v.context
                val intent = Intent(context, VendedorActivity::class.java)
                intent.putExtra("nickVendedor", usuarioNick.text.toString())
                context.startActivity(intent)
            }
        }

        editarUsuario.setOnClickListener { v ->
            editar()
        }
    }

    private fun readUsuario() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/usuario/$nick"

        var request = GsonJsonClassRequest(
            url,
            UsuariosData::class.java,
            Response.Listener { usuario ->
                usuarioNick.text = usuario.nick
                usuarioNome.text = usuario.nomeReal
                Picasso.get().load(usuario.foto).placeholder(R.drawable.no_photo).fit().centerCrop().into(usuarioFoto)
                usuarioEmail.text = "Email: " + usuario.email
                usuarioTel.text = "Telefone: " + usuario.contato.telefone
                usuarioEstado.text = "Estado: " + usuario.contato.estado
                usuarioCid.text = "Cidade: " + usuario.contato.cidade
                usuarioRua.text = "Rua: " + usuario.contato.rua
                usuarioNum.text = "Numero: " + usuario.contato.numero
                usuarioCEP.text = "CEP: " + usuario.contato.cep
            },
            Response.ErrorListener { e ->
                Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
            })

        queue?.add(request)
    }

    private fun ativarPerfilVendedor() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/$nick/vendedor/ativar"
        var vendedor = VendedorAtivar(cpfUsuario.text.toString())
        var post = Gson().toJson(vendedor)

        var request = GsonJsonRequest(Request.Method.POST, url, VendedorAtivar::class.java, post, Response.Listener { response ->
            finish()
        }, Response.ErrorListener { e ->
            Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
        })
        queue?.add(request)
    }

    private fun editar() {
        Toast.makeText(baseContext, "função de editar em desenvolvimento", Toast.LENGTH_SHORT).show()
    }

}
