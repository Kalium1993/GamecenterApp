package com.fundatec.gamecenter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
import com.fundatec.gamecenter.shared.Logado

import kotlinx.android.synthetic.main.activity_comunidades.*
import kotlinx.android.synthetic.main.content_comunidades.*

class ComunidadesActivity : AppCompatActivity() {

    private var queue : RequestQueue? = null
    private var pesquisa: String = ""
    private var nickLogado: String = ""
    private var idLogado: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comunidades)
        setSupportActionBar(toolbar)

        pesquisa = intent.getStringExtra("pesquisa")
        queue = Volley.newRequestQueue(baseContext)
        recyclerComunidades.layoutManager = LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)

        val logado = Logado(this)
        idLogado = logado.getLogadoId()
        nickLogado = logado.getLogadoNick()

        readComunidades()
        createComunidade()
        pesquisa()
    }

    private fun pesquisa() {
        searchComunidades.setOnSearchClickListener {
            criarComunidade.visibility = View.GONE
        }

        searchComunidades.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                criarComunidade.visibility = View.VISIBLE
                return false
            }
        })

        searchComunidades.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(pesquisa: String): Boolean {
                val intent = Intent(baseContext, ComunidadesActivity::class.java)
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

    private fun readComunidades() {
        var url = if(pesquisa.isEmpty())
            "https://gamecenter-api.herokuapp.com/gamecenter/comunidades"
        else
            "https://gamecenter-api.herokuapp.com/gamecenter/pesquisar/comunidades/q=$pesquisa"

        var request = GsonRequest(url, Array<ComunidadesData>::class.java, null, Response.Listener { response ->
                var lista = ArrayList(response.toList())
                if(lista.isEmpty() && pesquisa.isNotEmpty()) {
                    recyclerComunidades.visibility = View.GONE
                    emptyCmms.visibility = View.VISIBLE
                    emptyCmms.text = "Não foram encontrados resultados para a pesquisa: '$pesquisa'"
                } else if (lista.isEmpty() && pesquisa.isEmpty()) {
                    recyclerComunidades.visibility = View.GONE
                    emptyCmms.visibility = View.VISIBLE
                    emptyCmms.text = "Ainda não existem comunidades, crie a sua ;)"
                }

                var adapter = ComunidadesAdapter(baseContext, lista)
                recyclerComunidades.adapter = adapter

            },
            Response.ErrorListener { error ->
                Toast.makeText(baseContext, "" + error.message, Toast.LENGTH_LONG).show()
            }
        )
        queue?.add(request)
    }

    private fun createComunidade() {
        if(nickLogado.isEmpty() && idLogado.isEmpty()) {
            val alerta = AlertDialog.Builder(this)
            alerta.setMessage("Você precisa efetuar login para concluir a compra.")
            alerta.setCancelable(false)
            alerta.setNegativeButton("Cancelar") { dialog, which ->

            }
            alerta.setPositiveButton("OK"){ dialog, which ->
                val intent = Intent(baseContext, LoginActivity::class.java)
                startActivity(intent)
            }
            alerta.show()
        } else {
            criarComunidade.setOnClickListener { v ->
                val context = v.context
                val intent = Intent(context, ComunidadePostActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

}
