package com.fundatec.gamecenter.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.ProdutoPostActivity

import com.fundatec.gamecenter.R
import com.fundatec.gamecenter.jsonData.VendedoresData
import com.fundatec.gamecenter.request.GsonJsonClassRequest
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_vendedor.*
import kotlinx.android.synthetic.main.fragment_vendedor.*

private const val NICK_VENDEDOR = "nickVendedor"

class VendedorFragment : Fragment() {

    private var nickVendedor: String? = null
    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nickVendedor = it.getString(NICK_VENDEDOR)
        }

        queue = Volley.newRequestQueue(activity?.baseContext)

        readVendedor()

        anunciarProduto2.setOnClickListener { v ->
            val context =  v.context
            val intent = Intent(context, ProdutoPostActivity::class.java)
            intent.putExtra("nickVendedor", vendedorNick.text.toString())
            context.startActivity(intent)
        }

    }
    
    private fun readVendedor() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/vendedor/$nickVendedor"

        val request = GsonJsonClassRequest(
            url,
            VendedoresData::class.java,
            Response.Listener { vendedor ->
                vendedorNick2.text = vendedor.usuario!!.nick
                Picasso.get().load(vendedor.usuario!!.foto).placeholder(R.drawable.no_photo).fit().centerCrop().into(fotoVendedor2)

                if (vendedor.usuario!!.nomeReal == null) {
                    nomeVendedor2.text = ""
                } else {
                    nomeVendedor2.text = "(${vendedor.usuario!!.nomeReal})"
                }

                notaVendedor2.text = "Nota de Vendedor: " + vendedor.notaVendedor
            },
            Response.ErrorListener { error ->
                Toast.makeText(activity?.baseContext, error.message, Toast.LENGTH_SHORT).show()
            }
        )
        queue?.add(request)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vendedor, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            VendedorProdutosFragment().apply {
                arguments = Bundle().apply {
                    putString(NICK_VENDEDOR, param1)
                }
            }
    }

}

