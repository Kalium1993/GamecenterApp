package com.fundatec.gamecenter

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.jsonData.ProdutosData
import com.fundatec.gamecenter.request.GsonJsonClassRequest
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
}
