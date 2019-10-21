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
import com.fundatec.gamecenter.adapter.ProdutosAdapter
import com.fundatec.gamecenter.jsonData.ProdutosData
import com.fundatec.gamecenter.request.GsonRequest
import kotlinx.android.synthetic.main.fragment_pesquisa_produtos.*

private const val PESQUISA = "pesquisa"

class PesquisaProdutosFragment : Fragment() {
    private var pesquisa: String? = null
    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pesquisa = it.getString(PESQUISA)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queue = Volley.newRequestQueue(activity?.baseContext)
        recyclerPesquisaProdutos.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        readProdutos()
    }

    private fun readProdutos() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/pesquisar/produtos/q=$pesquisa"

        var request = GsonRequest(
            url, Array<ProdutosData>::class.java, null, Response.Listener { response ->
                var adapter =
                    ProdutosAdapter(activity!!.baseContext, ArrayList(response.toList()))
                recyclerPesquisaProdutos.adapter = adapter

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
        return inflater.inflate(R.layout.fragment_pesquisa_produtos, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            PesquisaProdutosFragment().apply {
                arguments = Bundle().apply {
                    putString(PESQUISA, param1)
                }
            }
    }
}
