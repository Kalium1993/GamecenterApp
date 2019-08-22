package com.fundatec.gamecenter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.adapter.NewsAdapter
import com.fundatec.gamecenter.jsonData.Article
import com.fundatec.gamecenter.jsonData.NewsData
import com.fundatec.gamecenter.request.GsonRequest

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        queue = Volley.newRequestQueue(baseContext)
        recyclerNews.layoutManager = LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)
        readNews()

    }

    private fun readNews() {
        var url = "https://newsapi.org/v2/top-headlines?sources=ign&apiKey=b736858e054e4280b22eb019cab26a91"

        var request = GsonRequest(
            url, NewsData::class.java, null, Response.Listener { response ->
                var adapter =
                    NewsAdapter(baseContext, response.articles as ArrayList<Article>)
                recyclerNews.adapter = adapter

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

            R.id.action_produtos ->{
                this.startActivity(Intent(this,ProdutosActivity::class.java))

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
