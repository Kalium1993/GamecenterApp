package com.fundatec.gamecenter

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.adapter.NewsAdapter
import com.fundatec.gamecenter.jsonData.Article
import com.fundatec.gamecenter.jsonData.NewsData
import com.fundatec.gamecenter.request.GsonRequest

import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.content_news.*

class NewsActivity : AppCompatActivity() {

    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
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
}
