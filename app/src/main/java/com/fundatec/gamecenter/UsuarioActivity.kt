package com.fundatec.gamecenter

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
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
import com.fundatec.gamecenter.shared.Logado
import com.google.gson.Gson
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_usuario.*
import kotlinx.android.synthetic.main.content_usuario.*

class UsuarioActivity : AppCompatActivity() {

    private var nick: String = ""
    private var senha: String = ""
    private var queue : RequestQueue? = null
    private var idUsuario: String = ""
    private var nickLogado: String = ""
    private var idLogado: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)
        setSupportActionBar(toolbar)

        nick = intent.getStringExtra("nick")
        senha = intent.getStringExtra("senha")
        queue = Volley.newRequestQueue(baseContext)
        val logado = Logado(this)

        readUsuario(logado)

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

        acessarPerfilVendedor.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, VendedorActivity::class.java)
            intent.putExtra("nickVendedor", nick)
            context.startActivity(intent)
        }
    }

    private fun readUsuario(logado: Logado) {
        var url = if(senha.isEmpty())
            "https://gamecenter-api.herokuapp.com/gamecenter/usuario/$nick"
        else
            "https://gamecenter-api.herokuapp.com/gamecenter/usuario/$nick/$senha/logar"

        var request = GsonJsonClassRequest(
            url,
            UsuariosData::class.java,
            Response.Listener { usuario ->
                if (usuario != null) {
                    if (senha.isNotEmpty()) {
                        logado.setLogadoId(usuario.id!!)
                        logado.setLogadoNick(usuario.nick)
                    }

                    usuarioNick.setText(usuario.nick)
                    usuarioNickView.text = usuario.nick

                    if (usuario.nomeReal != null) {
                        usuarioNome.setText(usuario.nomeReal)
                        usuarioNomeView.text = "(${usuario.nomeReal})"
                    }

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

                    if (usuario.vendedor!!) {
                        acessarPerfilVendedor.visibility = View.VISIBLE
                        ShowAtivarVendedor.text = "Desativar perfil de vendedor"
                        ShowAtivarVendedor.setOnClickListener {
                            val alerta = AlertDialog.Builder(this)
                            alerta.setMessage("Ao desativar seu perfil de vendedor, todos seus produtos à venda também serão excluídos, deseja continuar?")
                            alerta.setCancelable(false)
                            alerta.setNegativeButton("Cancelar") { dialog, which ->

                            }
                            alerta.setPositiveButton("Confirmar"){ dialog, which ->
                                desativarPerfilVendedor()
                            }
                            alerta.show()
                        }
                    }
                    idUsuario = usuario.id!!

                    idLogado = logado.getLogadoId()
                    nickLogado = logado.getLogadoNick()

                    if (idUsuario == idLogado) {
                        usuarioFoto.setOnClickListener {
                            urlFoto.visibility = View.VISIBLE
                        }

                        btnDeslogar.visibility = View.VISIBLE
                        btnDeslogar.setOnClickListener {
                            deslogar(logado)
                        }
                    } else {
                        endContato.visibility = View.GONE
                        txtEmail.visibility = View.GONE
                        usuarioEmail.visibility = View.GONE
                        txtTel.visibility = View.GONE
                        usuarioTel.visibility = View.GONE
                        txtEstado.visibility = View.GONE
                        usuarioEstado.visibility = View.GONE
                        txtCidade.visibility = View.GONE
                        usuarioCid.visibility = View.GONE
                        txtRua.visibility = View.GONE
                        usuarioRua.visibility = View.GONE
                        txtNum.visibility = View.GONE
                        usuarioNum.visibility = View.GONE
                        txtCEP.visibility = View.GONE
                        usuarioCEP.visibility = View.GONE
                        editarUsuario.visibility = View.GONE
                        deletarUsuario.visibility = View.GONE
                        ShowAtivarVendedor.visibility = View.GONE
                        usuarioNick.visibility = View.GONE
                        usuarioNome.visibility = View.GONE
                        usuarioSenha.visibility = View.GONE
                        usuarioNomeView.visibility = View.VISIBLE
                        usuarioNickView.visibility = View.VISIBLE
                    }

                } else {
                    Toast.makeText( baseContext, "Não foi encontrado nenhum usuário com o nick/email e senha informados", Toast.LENGTH_LONG).show()
                    val intent = Intent(baseContext, LoginActivity::class.java)
                    startActivity(intent)
                }
            },
            Response.ErrorListener { e ->
                Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
            })

        queue?.add(request)
    }

    private fun ativarPerfilVendedor() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/$nick/vendedor/ativar"
        var vendedor = VendedoresData(cpfUsuario.text.toString())
        var post = Gson().toJson(vendedor)

        var request = GsonJsonRequest(Request.Method.POST, url, VendedoresData::class.java, post, Response.Listener { response ->
            val intent = Intent(baseContext, VendedorActivity::class.java)
            intent.putExtra("nickVendedor", usuarioNick.text.toString())
            startActivity(intent)
        }, Response.ErrorListener { e ->
            Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
        })
        queue?.add(request)
    }

    private fun desativarPerfilVendedor() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/vendedor/$nick/delete"
        var vendedor = VendedoresData()
        var delete = Gson().toJson(vendedor)

        var request = GsonJsonRequest(Request.Method.DELETE, url, VendedoresData::class.java, delete, Response.Listener { response ->
            val intent = Intent(baseContext, UsuarioActivity::class.java)
            intent.putExtra("nick", usuarioNick.text.toString())
            intent.putExtra("senha", "")
            startActivity(intent)
            Toast.makeText(baseContext, "Perfil de vendedor desativado", Toast.LENGTH_SHORT).show()
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

        var usuario = UsuariosData(contato, usuarioEmail.text.toString(), foto, usuarioNick.text.toString(), nomeReal, usuarioSenha.text.toString())
        var put = Gson().toJson(usuario)

        var request = GsonJsonRequest(Request.Method.PUT, url, UsuariosData::class.java, put, Response.Listener {
            val intent = Intent(baseContext, UsuarioActivity::class.java)
            intent.putExtra("nick", usuarioNick.text.toString())
            intent.putExtra("senha", usuarioSenha.text.toString())
            startActivity(intent)
        }, Response.ErrorListener { e ->
            Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
        })

        queue?.add(request)
    }

    private fun deletar() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/usuario/$idUsuario/delete"

        var usuario = UsuariosData()
        var delete = Gson().toJson(usuario)

        var request = GsonJsonRequest(Request.Method.DELETE, url, UsuariosData::class.java, delete, Response.Listener { v ->
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(baseContext, "Perfil Excluído", Toast.LENGTH_SHORT).show()
        }, Response.ErrorListener { e ->
            Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
        })

        queue?.add(request)
    }

    private fun deslogar(logado: Logado) {
        logado.setLogadoId("")
        logado.setLogadoNick("")
        val intent = Intent(baseContext, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(baseContext, "Deslogado(a) com sucesso", Toast.LENGTH_SHORT).show()
    }

}
