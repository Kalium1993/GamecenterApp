package com.fundatec.gamecenter

import android.content.Intent
import android.graphics.Paint
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
import com.fundatec.gamecenter.adapter.TopicosAdapter
import com.fundatec.gamecenter.jsonData.ComunidadesData
import com.fundatec.gamecenter.jsonData.TopicosData
import com.fundatec.gamecenter.request.GsonJsonClassRequest
import com.fundatec.gamecenter.request.GsonJsonRequest
import com.fundatec.gamecenter.request.GsonRequest
import com.google.gson.Gson
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_comunidade2.*
import kotlinx.android.synthetic.main.content_comunidade2.*

class ComunidadeActivity2 : AppCompatActivity() {

    private var queue : RequestQueue? = null
    private var idComunidade: String = ""
    private var idTopico: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comunidade2)
        setSupportActionBar(toolbar)

        idComunidade = intent.getStringExtra("idComunidade")
        queue = Volley.newRequestQueue(baseContext)
        recyclerTopicos2.layoutManager = LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)

        addTopico2.paintFlags = addTopico2.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        readComunidade()
        readTopicos()
        criarTopico()
    }

    private fun readComunidade() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/comunidade/$idComunidade"

        val request = GsonJsonClassRequest(
            url,
            ComunidadesData::class.java,
            Response.Listener { comunidade ->
                nomeCmm2.text = comunidade.nome
                Picasso.get().load(comunidade.imagem).placeholder(R.drawable.no_img).fit().centerCrop().into(imagemCmm2)
                descricaoCmm2.text = comunidade.descricao

                deleteCmm2.setOnClickListener {
                    val alerta = AlertDialog.Builder(this)
                    alerta.setMessage("Ao deletar a comunidade, todos os tópicos, e as mensagens dos mesmos, também serão excluídos, deseja continuar?")
                    alerta.setCancelable(false)
                    alerta.setNegativeButton("Cancelar") { dialog, which ->

                    }
                    alerta.setPositiveButton("Confirmar"){ dialog, which ->
                        deleteComuidade()
                    }
                    alerta.show()
                }

            },
            Response.ErrorListener { error ->
                Toast.makeText(baseContext, "" + error.message, Toast.LENGTH_SHORT).show()
            }
        )
        queue?.add(request)
    }

    private fun readTopicos() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/comunidade/$idComunidade/topicos"
        var request = GsonRequest(
            url, Array<TopicosData>::class.java, null, Response.Listener { response ->
                var adapter = TopicosAdapter(baseContext, ArrayList(response.toList()))
                recyclerTopicos2.adapter = adapter
            },
            Response.ErrorListener { error ->
                Toast.makeText(baseContext, "" + error.message, Toast.LENGTH_LONG).show()
            }
        )
        queue?.add(request)
    }

    private fun deleteComuidade() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/comunidade/$idComunidade/delete"

        var comunidade = ComunidadesData()
        var delete = Gson().toJson(comunidade)

        var request = GsonJsonRequest(Request.Method.DELETE, url, ComunidadesData::class.java, delete, Response.Listener { v ->
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(baseContext, "Comunidade Excluída", Toast.LENGTH_SHORT).show()
        }, Response.ErrorListener { e ->
            Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
        })

        queue?.add(request)
    }

    private fun criarTopico() {
        addTopico2.setOnClickListener { v ->
            val context =  v.context
            val intent = Intent(context, TopicoPostActivity::class.java)
            intent.putExtra("idComunidade", idComunidade)
            context.startActivity(intent)
        }
    }

}
