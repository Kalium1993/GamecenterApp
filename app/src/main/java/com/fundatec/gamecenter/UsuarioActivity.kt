package com.fundatec.gamecenter

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.jsonData.*
import com.fundatec.gamecenter.request.GsonJsonClassRequest
import com.fundatec.gamecenter.request.GsonJsonRequest
import com.google.gson.Gson
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_usuario.*
import kotlinx.android.synthetic.main.content_usuario.*

class UsuarioActivity : AppCompatActivity() {

    private var nick: String = ""
    private var queue : RequestQueue? = null
    private var idUsuario: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)
        setSupportActionBar(toolbar)

        nick = intent.getStringExtra("nick")
        queue = Volley.newRequestQueue(baseContext)
        readUsuario()

        ShowAtivarVendedor.setOnClickListener {
            msgCPF.visibility = View.VISIBLE
            cpfUsuario.visibility = View.VISIBLE
            ativarVendedor.visibility = View.VISIBLE

            ativarVendedor.setOnClickListener {
                ativarPerfilVendedor()
            }
        }

        editarUsuario.setOnClickListener {
            editar()
        }

        deletarUsuario.setOnClickListener {
            val alerta = AlertDialog.Builder(this)
            alerta.setMessage("Ao excluir seu perfil, o seu perfil de vendedor será desativado e seus produtos à venda também serão excluídos, deseja continuar?")
            alerta.setCancelable(false)
            alerta.setNegativeButton("Cancelar") { dialog, which ->

                }
            alerta.setPositiveButton("Confirmar"){ dialog, which ->
                    deletar()
                }
            alerta.show()
        }

        usuarioFoto.setOnClickListener {
            urlFoto.visibility = View.VISIBLE
        }

    }

    private fun readUsuario() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/usuario/$nick"

        var request = GsonJsonClassRequest(
            url,
            UsuariosData::class.java,
            Response.Listener { usuario ->
                usuarioNick.setText(usuario.nick)

                if (usuario.nomeReal != null)
                    usuarioNome.setText(usuario.nomeReal)

                Picasso.get().load(usuario.foto).placeholder(R.drawable.no_photo).fit().centerCrop().into(usuarioFoto)
                urlFoto.setText(usuario.foto)
                usuarioEmail.setText(usuario.email)
                usuarioSenha.setText(usuario.senha)

                if(usuario.contato != null) {
                    usuarioTel.setText(usuario.contato!!.telefone)
                    usuarioEstado.setText(usuario.contato!!.estado)
                    usuarioCid.setText(usuario.contato!!.cidade)
                    usuarioRua.setText(usuario.contato!!.rua)
                    usuarioNum.setText(usuario.contato!!.numero)
                    usuarioCEP.setText(usuario.contato!!.cep)
                }

                if (usuario.vendedor!!)
                    ShowAtivarVendedor.visibility = View.GONE

                idUsuario = usuario.id!!

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
            val intent = Intent(baseContext, VendedorActivity::class.java)
            intent.putExtra("nickVendedor", usuarioNick.text.toString())
            startActivity(intent)
        }, Response.ErrorListener { e ->
            Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
        })
        queue?.add(request)
    }

    private fun editar() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/usuario/$idUsuario/edit"
        var contato = Contato(usuarioCEP.text.toString(), usuarioCid.text.toString(), usuarioEstado.text.toString(), usuarioNum.text.toString(), usuarioRua.text.toString(), usuarioTel.text.toString())
        var foto: String ?= null
        if (urlFoto.text.toString().trim().isNotEmpty())
            foto = urlFoto.text.toString()

        var nomeReal: String ?= null
        if (usuarioNome.text.toString().trim().isNotEmpty())
            nomeReal = usuarioNome.text.toString()

        var usuario = UsuarioEditar(contato, usuarioEmail.text.toString(), foto, usuarioNick.text.toString(), nomeReal, usuarioSenha.text.toString())
        var put = Gson().toJson(usuario)

        var request = GsonJsonRequest(Request.Method.PUT, url, UsuarioEditar::class.java, put, Response.Listener {
            val intent = intent
            finish()
            intent.putExtra("nick", usuarioNick.text.toString())
            startActivity(intent)
        }, Response.ErrorListener { e ->
            Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
        })

        queue?.add(request)
    }

    private fun deletar() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/usuario/$idUsuario/delete"

        var usuario = UsuarioDeletar(idUsuario)
        var delete = Gson().toJson(usuario)

        var request = GsonJsonRequest(Request.Method.DELETE, url, UsuarioEditar::class.java, delete, Response.Listener { v ->
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(baseContext, "Perfil Excluído", Toast.LENGTH_SHORT).show()
        }, Response.ErrorListener { e ->
            Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
        })

        queue?.add(request)
    }

}
