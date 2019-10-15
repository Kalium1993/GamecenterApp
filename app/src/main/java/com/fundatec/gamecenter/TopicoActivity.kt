package com.fundatec.gamecenter

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.adapter.MensagensAdapter
import com.fundatec.gamecenter.jsonData.MensagensData
import com.fundatec.gamecenter.jsonData.TopicosData
import com.fundatec.gamecenter.request.GsonJsonClassRequest
import com.fundatec.gamecenter.request.GsonRequest

import kotlinx.android.synthetic.main.activity_topico.*
import kotlinx.android.synthetic.main.content_topico.*

class TopicoActivity : AppCompatActivity() {

    private var idComunidade: String = ""
    private var idTopico: String = ""
    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topico)
        setSupportActionBar(toolbar)

        idComunidade = intent.getStringExtra("idComunidade")
        idTopico = intent.getStringExtra("idTopico")
        queue = Volley.newRequestQueue(baseContext)
        recyclerMensagens.layoutManager = LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)

        readTopico()
        readMensagens()

        enviarMsg.setOnClickListener {
            enviar()
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
        Toast.makeText( baseContext, "desenvolvendo", Toast.LENGTH_LONG).show()
    }

}
