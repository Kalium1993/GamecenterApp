package com.fundatec.gamecenter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.adapter.MensagensAdapter
import com.fundatec.gamecenter.jsonData.MensagensData
import com.fundatec.gamecenter.jsonData.TopicosData
import com.fundatec.gamecenter.request.GsonJsonClassRequest
import com.fundatec.gamecenter.request.GsonJsonRequest
import com.fundatec.gamecenter.request.GsonRequest
import com.fundatec.gamecenter.shared.Logado
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_topico.*
import kotlinx.android.synthetic.main.content_topico.*

class TopicoActivity : AppCompatActivity() {

    private var idComunidade: String = ""
    private var idTopico: String = ""
    private var nickLogado: String = ""
    private var idLogado: String = ""
    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topico)
        setSupportActionBar(toolbar)

        idComunidade = intent.getStringExtra("idComunidade")
        idTopico = intent.getStringExtra("idTopico")
        queue = Volley.newRequestQueue(baseContext)
        recyclerMensagens.layoutManager = LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)

        val logado = Logado(this)
        idLogado = logado.getLogadoId()
        nickLogado = logado.getLogadoNick()

        readTopico()
        readMensagens()

        enviarMsg.setOnClickListener {
            enviar()
        }

        delTopico.setOnClickListener {
            if(nickLogado.isEmpty() && idLogado.isEmpty()) {
                val alerta = AlertDialog.Builder(this)
                alerta.setMessage("Você precisa efetuar login para deletar o tópico.")
                alerta.setCancelable(false)
                alerta.setNegativeButton("Cancelar") { dialog, which ->

                }
                alerta.setPositiveButton("OK"){ dialog, which ->
                    val intent = Intent(baseContext, LoginActivity::class.java)
                    startActivity(intent)
                }
                alerta.show()
            } else {
                val alerta = AlertDialog.Builder(this)
                alerta.setMessage("Ao deletar o Tópico, todas as mensagens do mesmo também serão deletadas, e esta ação não poderá ser desfeita, confirmar?")
                alerta.setCancelable(false)
                alerta.setNegativeButton("Cancelar") { dialog, which ->

                }
                alerta.setPositiveButton("Confirmar"){ dialog, which ->
                    deletarTopico()
                }
                alerta.show()
            }
        }

    }

    private fun readTopico() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/comunidade/$idComunidade/topico/$idTopico"

        val request = GsonJsonClassRequest(
            url,
            TopicosData::class.java,
            Response.Listener { topico ->
                tituloTopico.text = topico.titulo
            },
            Response.ErrorListener { error ->
                Toast.makeText(baseContext, "" + error.message, Toast.LENGTH_SHORT).show()
            }
        )
        queue?.add(request)
    }

    private fun readMensagens() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/comunidade/$idComunidade/topico/$idTopico/mensagens"

        var request = GsonRequest(
            url, Array<MensagensData>::class.java, null, Response.Listener { response ->
                var adapter =
                    MensagensAdapter(baseContext, ArrayList(response.toList()))
                recyclerMensagens.adapter = adapter

            },
            Response.ErrorListener { error ->
                Toast.makeText(baseContext, "" + error.message, Toast.LENGTH_LONG).show()
            }
        )
        queue?.add(request)
    }

    private fun enviar() {
        if(nickLogado.isEmpty() && idLogado.isEmpty()) {
            val alerta = AlertDialog.Builder(this)
            alerta.setMessage("Você precisa efetuar login para postar uma mensagem.")
            alerta.setCancelable(false)
            alerta.setNegativeButton("Cancelar") { dialog, which ->

            }
            alerta.setPositiveButton("OK"){ dialog, which ->
                val intent = Intent(baseContext, LoginActivity::class.java)
                startActivity(intent)
            }
            alerta.show()
        } else {
            var url = "https://gamecenter-api.herokuapp.com/gamecenter/comunidade/$idComunidade/topico/$idTopico/mensagem/$idLogado/post"

            var mensagem = MensagensData(mensagemEnviar.text.toString())
            var post = Gson().toJson(mensagem)

            var request = GsonJsonRequest(Request.Method.POST, url, MensagensData::class.java, post, Response.Listener { response ->
                val intent = Intent(baseContext, TopicoActivity::class.java)
                intent.putExtra("idComunidade", idComunidade)
                intent.putExtra("idTopico", idTopico)
                startActivity(intent)
            }, Response.ErrorListener { e ->
                Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
            })
            queue?.add(request)
        }
    }

    private fun deletarTopico() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/comunidade/$idComunidade/topico/$idTopico/delete"
        var topico = MensagensData()
        var delete = Gson().toJson(topico)

        var request = GsonJsonRequest(Request.Method.DELETE, url, MensagensData::class.java, delete, Response.Listener {
            val intent = Intent(baseContext, ComunidadeActivity::class.java)
            intent.putExtra("idComunidade", idComunidade)
            startActivity(intent)
        }, Response.ErrorListener { e ->
            Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
        })
        queue?.add(request)
    }

}
