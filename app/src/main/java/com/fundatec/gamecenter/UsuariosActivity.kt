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
import com.fundatec.gamecenter.adapter.UsuariosAdapter
import com.fundatec.gamecenter.jsonData.UsuariosData
import com.fundatec.gamecenter.request.GsonRequest

import kotlinx.android.synthetic.main.activity_usuarios.*
import kotlinx.android.synthetic.main.content_usuarios.*

class UsuariosActivity : AppCompatActivity() {

    private var query: String = ""
    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuarios)
        setSupportActionBar(toolbar)

        query = intent.getStringExtra("query")

        queue = Volley.newRequestQueue(baseContext)
        recyclerUsuarios.layoutManager = LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)
        readUsuario()
    }

    private fun readUsuario() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/pesquisar/usuarios/q=$query"

        var request = GsonRequest(
            url, Array<UsuariosData>::class.java, null, Response.Listener { response ->
                var adapter =
                    UsuariosAdapter(baseContext, ArrayList(response.toList()))
                recyclerUsuarios.adapter = adapter
            },
            Response.ErrorListener { error ->
                Toast.makeText(baseContext, "" + error.message, Toast.LENGTH_LONG).show()
            }
        )
        queue?.add(request)
    }
}
