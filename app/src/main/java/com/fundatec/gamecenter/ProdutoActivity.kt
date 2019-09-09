package com.fundatec.gamecenter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.jsonData.ProdutoCompra
import com.fundatec.gamecenter.jsonData.ProdutosData
import com.fundatec.gamecenter.request.GsonJsonClassRequest
import com.fundatec.gamecenter.request.GsonJsonRequest
import com.google.gson.Gson
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_produto.*
import kotlinx.android.synthetic.main.content_produto.*

class ProdutoActivity : AppCompatActivity() {

    private var nickVendedor: String = ""
    private var idProduto: String = ""

    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produto)
        setSupportActionBar(toolbar)

        nickVendedor = intent.getStringExtra("nickVendedor")
        idProduto = intent.getStringExtra("idProduto")

        queue = Volley.newRequestQueue(baseContext)
        readProduto()

        comprarProduto.setOnClickListener {
            findViewById<EditText>(R.id.cmmCompra).visibility = View.VISIBLE
            findViewById<EditText>(R.id.notaVenda).visibility = View.VISIBLE
            findViewById<Button>(R.id.confirmar).visibility = View.VISIBLE

            confirmar.setOnClickListener { v ->
                comprar()
                val context =  v.context
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                Toast.makeText(baseContext, "Compra Realizada com sucesso!", Toast.LENGTH_SHORT).show()
            }

        }

        perguntarProduto.setOnClickListener { v ->
            perguntar()
        }
    }

    private fun readProduto() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/$nickVendedor/produto/$idProduto"

        val request = GsonJsonClassRequest(
            url,
            ProdutosData::class.java,
            Response.Listener { produto ->
                vendedor.text = "Vendedor: " + produto.nickVendedor
                Picasso.get().load(produto.imagem).placeholder(R.drawable.no_img).fit().centerCrop().into(imagemProdutoP)
                valorProduto.text = "Valor: R$" + produto.valor
                freteProduto.text = "Frete: R$" + produto.frete
                nomeProdutoP.text = produto.nome
                descricaoProdutoP.text = produto.descricao

                vendedor.setOnClickListener { v ->
                    val context = v.context
                    val intent = Intent(context, VendedorActivity::class.java)
                    intent.putExtra("nickVendedor", produto.nickVendedor)
                    context.startActivity(intent)
                }

            },
            Response.ErrorListener { error ->
                Toast.makeText(baseContext, error.message, Toast.LENGTH_SHORT).show()
            }
        )
        queue?.add(request)
    }

    private fun comprar() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/$nickVendedor/produto/$idProduto/vendido"
        var nota = notaVenda.text.toString()
        var produto = ProdutoCompra(cmmCompra.text.toString(), nota.toDouble())
        var edit = Gson().toJson(produto)

        var request = GsonJsonRequest(Request.Method.PUT, url, ProdutoCompra::class.java, edit, Response.Listener { response ->
            finish()
        }, Response.ErrorListener { e ->
            Toast.makeText( baseContext, "" + e.message, Toast.LENGTH_LONG).show()
        })
        queue?.add(request)
    }

    private fun perguntar() {
        Toast.makeText(baseContext, "função de perguntar em desenvolvimento", Toast.LENGTH_SHORT).show()
    }

}