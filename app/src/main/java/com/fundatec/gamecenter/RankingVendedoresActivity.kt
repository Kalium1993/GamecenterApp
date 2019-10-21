package com.fundatec.gamecenter

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.adapter.RankingVendedoresAdapter
import com.fundatec.gamecenter.jsonData.VendedoresData
import com.fundatec.gamecenter.request.GsonRequest

import kotlinx.android.synthetic.main.activity_ranking_vendedores.*
import kotlinx.android.synthetic.main.content_ranking_vendedores.*

class RankingVendedoresActivity : AppCompatActivity() {

    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking_vendedores)
        setSupportActionBar(toolbar)

        queue = Volley.newRequestQueue(baseContext)
        recyclerRankingVendedores.layoutManager = LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)
        readVendedores()

    }

    private fun readVendedores() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/Ranking-Vendedores"

        var request = GsonRequest(
            url, Array<VendedoresData>::class.java, null, Response.Listener { response ->
                var adapter =
                    RankingVendedoresAdapter(baseContext, ArrayList(response.toList()))
                recyclerRankingVendedores.adapter = adapter

            },
            Response.ErrorListener { error ->
                Toast.makeText(baseContext, "" + error.message, Toast.LENGTH_LONG).show()
            }
        )
        queue?.add(request)
    }
}
