package com.fundatec.gamecenter.fragment

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

private const val NICK_VENDEDOR = "nickVendedor"

class VendedorProdutosVendaFragment : Fragment() {

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
        queue = Volley.newRequestQueue(activity?.baseContext)
        recyclerProdutosVendedorVenda.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        readProdutosVendedor()

    }

    private fun readProdutosVendedor() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/$nickVendedor/produtos/a-venda"

        var request = GsonRequest(url, Array<ProdutosData>::class.java, null, Response.Listener { response ->
                var lista = ArrayList(response.toList())
                if(lista.isEmpty()) {
                    recyclerProdutosVendedorVenda.visibility = View.GONE
                    emptyVendas.visibility = View.VISIBLE
                }

                var adapter = ProdutosVendedorAdapter(activity!!.baseContext, lista)
                recyclerProdutosVendedorVenda.adapter = adapter
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
        return inflater.inflate(R.layout.fragment_vendedor_produtos_venda, container, false)
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            VendedorProdutosVendaFragment().apply {
                arguments = Bundle().apply {
                    putString(NICK_VENDEDOR, param1)
                }
            }
    }
}
