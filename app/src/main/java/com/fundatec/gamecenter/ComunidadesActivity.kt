package com.fundatec.gamecenter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.adapter.ComunidadesAdapter
import com.fundatec.gamecenter.jsonData.ComunidadesData
import com.fundatec.gamecenter.request.GsonRequest

import kotlinx.android.synthetic.main.activity_comunidades.*
import kotlinx.android.synthetic.main.content_comunidades.*

class ComunidadesActivity : AppCompatActivity() {

    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comunidades)
        setSupportActionBar(toolbar)

        queue = Volley.newRequestQueue(baseContext)
        recyclerComunidades.layoutManager = LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)
        readComunidades()
        createComunidade()

    }

    private fun readComunidades() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/comunidades"

        var request = GsonRequest(
            url, Array<ComunidadesData>::class.java, null, Response.Listener { response ->
                var adapter =
                    ComunidadesAdapter(baseContext, ArrayList(response.toList()))
                recyclerComunidades.adapter = adapter

            },
            Response.ErrorListener { error ->
                Toast.makeText(baseContext, "" + error.message, Toast.LENGTH_LONG).show()
            }
        )
        queue?.add(request)
    }

    private fun createComunidade() {
        criarComunidade.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, ComunidadePostActivity::class.java)
            context.startActivity(intent)
        }
    }

}
