package com.fundatec.gamecenter

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        openActivities()
        pesquisa()
    }

    private fun pesquisa() {
        pesquisar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(pesquisa: String): Boolean {
                val intent = Intent(baseContext, PesquisaGeralActivity::class.java)
                intent.putExtra("pesquisa", pesquisa)
                startActivity(intent)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // do something when text changes
                return false
            }
        })
    }

    private fun openActivities() {
        openProdutos.setOnClickListener {
            val intent = Intent(baseContext, ProdutosActivity::class.java)
            startActivity(intent)
        }

        openNews.setOnClickListener {
            val intent = Intent(baseContext, NewsActivity::class.java)
            startActivity(intent)
        }

        openRankingVendedores.setOnClickListener {
            val intent = Intent(baseContext, RankingVendedoresActivity::class.java)
            startActivity(intent)
        }

        openLogin.setOnClickListener {
            val intent = Intent(baseContext, LoginActivity::class.java)
            startActivity(intent)
        }

        openComunidades.setOnClickListener {
            val intent = Intent(baseContext, ComunidadesActivity::class.java)
            startActivity(intent)
        }
    }
}
