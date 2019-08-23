package com.fundatec.gamecenter

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.adapter.ProdutosAdapter
import com.fundatec.gamecenter.adapter.RankingVendedoresAdapter
import com.fundatec.gamecenter.jsonData.VendedoresData
import com.fundatec.gamecenter.request.GsonRequest

import kotlinx.android.synthetic.main.activity_ranking_vendedores.*
import kotlinx.android.synthetic.main.content_produtos.*
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
                Toast.makeText(baseContext, "deu ruim " + error.message, Toast.LENGTH_LONG).show()
            }
        )
        queue?.add(request)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_home ->{
                this.startActivity(Intent(this,MainActivity::class.java))

                true
            }

            R.id.action_news ->{
                this.startActivity(Intent(this,NewsActivity::class.java))

                true
            }

            R.id.action_produtos ->{
                this.startActivity(Intent(this,ProdutosActivity::class.java))

                true
            }

            R.id.action_rankingVendedores ->{
                this.startActivity(Intent(this,RankingVendedoresActivity::class.java))

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}
