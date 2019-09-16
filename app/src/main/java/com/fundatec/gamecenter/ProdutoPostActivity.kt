package com.fundatec.gamecenter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.jsonData.ProdutoPost
import com.fundatec.gamecenter.request.GsonJsonRequest
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_produto_post.*
import kotlinx.android.synthetic.main.content_produto_post.*

class ProdutoPostActivity : AppCompatActivity() {

    private var nickVendedor: String = ""
    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produto_post)
        setSupportActionBar(toolbar)

        nickVendedor = intent.getStringExtra("nickVendedor")
        queue = Volley.newRequestQueue(baseContext)

        postProduto.setOnClickListener { v ->
            anunciar()
        }
    }

    private fun anunciar() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/$nickVendedor/produto/post"
        var frete = postFrete.text.toString()
        var valor = postValor.text.toString()

        var imagem: String ?= null
        if (postImagem.text.toString().trim().isNotEmpty())
            imagem = postImagem.text.toString()
        
        var produto = ProdutoPost(postDescricao.text.toString(), frete.toDouble(), imagem, postNome.text.toString(), valor.toDouble())
        var post = Gson().toJson(produto)

        var request = GsonJsonRequest(Request.Method.POST, url, ProdutoPost::class.java, post, Response.Listener { response ->
            val intent = Intent(baseContext, VendedorActivity::class.java)
            intent.putExtra("nickVendedor", nickVendedor)
            startActivity(intent)
        }, Response.ErrorListener { e ->
            Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
        })
        queue?.add(request)
    }

}
