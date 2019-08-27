package com.fundatec.gamecenter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        openActivities()
    }

    private fun openActivities() {
        var openProdutos = findViewById<ImageView>(R.id.openProdutos)

        openProdutos.setOnClickListener {
            val intent = Intent(this, ProdutosActivity::class.java)
            startActivity(intent)
        }

        var openNews = findViewById<ImageView>(R.id.openNews)

        openNews.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }

        var openRankingVendedores = findViewById<ImageView>(R.id.openRankingVendedores)

        openRankingVendedores.setOnClickListener {
            val intent = Intent(this, RankingVendedoresActivity::class.java)
            startActivity(intent)
        }
    }
}
