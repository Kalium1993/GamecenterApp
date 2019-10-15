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
import com.fundatec.gamecenter.jsonData.ComunidadesData
import com.fundatec.gamecenter.jsonData.MensagensData
import com.fundatec.gamecenter.jsonData.TopicosData
import com.fundatec.gamecenter.request.GsonJsonRequest
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_topico_post.*
import kotlinx.android.synthetic.main.content_topico_post.*

class TopicoPostActivity : AppCompatActivity() {

    private var idComunidade: String = ""
    private var idTopico: String = ""
    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topico_post)
        setSupportActionBar(toolbar)

        idComunidade = intent.getStringExtra("idComunidade")
        queue = Volley.newRequestQueue(baseContext)

        criarTopico.setOnClickListener {
            criar()
        }

    }

    private fun criar() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/comunidade/$idComunidade/topico/criar"

        var topico = TopicosData(tituloTopicoPost.text.toString())
        var post = Gson().toJson(topico)

        var request = GsonJsonRequest(Request.Method.POST, url, TopicosData::class.java, post, Response.Listener { response ->
            msgTopico.visibility = View.VISIBLE
            enviarTopico.visibility = View.VISIBLE
            idTopico = response.id.toString()

            enviarTopico.setOnClickListener {
                enviar()
            }

        }, Response.ErrorListener { e ->
            Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
        })
        queue?.add(request)
    }

    private fun enviar() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/comunidade/$idComunidade/topico/$idTopico/mensagem/post"

        var mensagaem = MensagensData(msgTopico.text.toString())
        var post = Gson().toJson(mensagaem)

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
