package com.fundatec.gamecenter.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley

import com.fundatec.gamecenter.R
import com.fundatec.gamecenter.adapter.ProdutosVendedorAdapter
import com.fundatec.gamecenter.jsonData.ProdutosData
import com.fundatec.gamecenter.request.GsonRequest
import kotlinx.android.synthetic.main.fragment_vendedor_produtos_venda.*
import kotlinx.android.synthetic.main.fragment_vendedor_produtos_vendidos.*

private const val NICK_VENDEDOR = "nickVendedor"

class VendedorProdutosVendidosFragment : Fragment() {
    private var nickVendedor: String? = null
    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nickVendedor = it.getString(NICK_VENDEDOR)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerProdutosVendedorVendidos.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        queue = Volley.newRequestQueue(activity?.baseContext)
        readProdutosVendedor()
    }

    private fun readProdutosVendedor() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/$nickVendedor/produtos/vendidos"

        var request = GsonRequest(url, Array<ProdutosData>::class.java, null, Response.Listener { response ->
                var lista = ArrayList(response.toList())
                if(lista.isEmpty()) {
                    recyclerProdutosVendedorVendidos.visibility = View.GONE
                    emptyVendidos.visibility = View.VISIBLE
                }

                var adapter = ProdutosVendedorAdapter(activity!!.baseContext, lista)
                recyclerProdutosVendedorVendidos.adapter = adapter
            },
            Response.ErrorListener { error ->
                Toast.makeText(activity?.baseContext, "" + error.message, Toast.LENGTH_LONG).show()
            }
        )
        queue?.add(request)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vendedor_produtos_vendidos, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            VendedorProdutosVendidosFragment().apply {
                arguments = Bundle().apply {
                    putString(NICK_VENDEDOR, param1)
                }
            }
    }
}
