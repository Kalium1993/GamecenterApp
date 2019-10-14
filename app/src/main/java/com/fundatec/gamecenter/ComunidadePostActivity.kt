package com.fundatec.gamecenter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.jsonData.ComunidadesData
import com.fundatec.gamecenter.request.GsonJsonRequest
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_comunidade_post.*
import kotlinx.android.synthetic.main.comunidades_list.*
import kotlinx.android.synthetic.main.content_comunidade_post.*

class ComunidadePostActivity : AppCompatActivity() {

    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comunidade_post)
        setSupportActionBar(toolbar)
        queue = Volley.newRequestQueue(baseContext)

        postComunidade.setOnClickListener {
            post()
        }

    }

    private fun post() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/comunidade/criar"

        var imagem: String ?= null
        if (postComunidadeDescricao.text.toString().trim().isNotEmpty())
            imagem = postComunidadeDescricao.text.toString()

        var comunidade = ComunidadesData(postComunidadeNome.text.toString(), postComunidadeDescricao.text.toString(), imagem)
        var post = Gson().toJson(comunidade)

        var request = GsonJsonRequest(Request.Method.POST, url, ComunidadesData::class.java, post, Response.Listener { response ->
            val intent = Intent(baseContext, ComunidadesActivity::class.java)
            startActivity(intent)
        }, Response.ErrorListener { e ->
            Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
        })
        queue?.add(request)
    }

}
