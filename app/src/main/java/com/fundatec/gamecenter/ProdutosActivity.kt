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
import com.fundatec.gamecenter.jsonData.ProdutosData
import com.fundatec.gamecenter.request.GsonRequest

import kotlinx.android.synthetic.main.activity_produtos.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_produtos.*

class ProdutosActivity : AppCompatActivity() {

    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produtos)
        setSupportActionBar(toolbar)

        queue = Volley.newRequestQueue(baseContext)
        recyclerProdutos.layoutManager = LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)
        readProdutos()

    }

    private fun readProdutos() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/produtos"

        var request = GsonRequest(
            url, ProdutosData::class.java, null, Response.Listener { response ->
                var adapter =
                    ProdutosAdapter(baseContext, response as ArrayList<ProdutosData>)
                recyclerProdutos.adapter = adapter

            },
            Response.ErrorListener { error ->
                Toast.makeText(baseContext, "deu ruim " + error.message, Toast.LENGTH_LONG).show()
            }
        )
        queue?.add(request)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_home -> {
                this.startActivity(Intent(this, MainActivity::class.java))

                true
            }

            R.id.action_produtos -> {
                this.startActivity(Intent(this, ProdutosActivity::class.java))

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
