package com.fundatec.gamecenter

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.adapter.VendedorAdapter
import com.fundatec.gamecenter.jsonData.ProdutosData
import com.fundatec.gamecenter.request.GsonRequest

import kotlinx.android.synthetic.main.activity_vendedor.*
import kotlinx.android.synthetic.main.content_vendedor.*

class VendedorActivity : AppCompatActivity() {

    private var nickVendedor: String = ""

    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendedor)
        setSupportActionBar(toolbar)

        nickVendedor = intent.getStringExtra("nickVendedor")

        queue = Volley.newRequestQueue(baseContext)
        recyclerProdutosVendedor.layoutManager = LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)
        readProdutosVendedor()
    }

    private fun readProdutosVendedor() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/$nickVendedor/produtos"

        var request = GsonRequest(
            url, Array<ProdutosData>::class.java, null, Response.Listener { response ->
                var adapter =
                    VendedorAdapter(baseContext, ArrayList(response.toList()))
                recyclerProdutosVendedor.adapter = adapter

            },
            Response.ErrorListener { error ->
                Toast.makeText(baseContext, "deu ruim " + error.message, Toast.LENGTH_LONG).show()
            }
        )
        queue?.add(request)
    }
}
