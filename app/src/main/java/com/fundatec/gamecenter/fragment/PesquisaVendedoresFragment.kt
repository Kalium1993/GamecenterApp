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
import com.fundatec.gamecenter.adapter.RankingVendedoresAdapter
import com.fundatec.gamecenter.jsonData.VendedoresData
import com.fundatec.gamecenter.request.GsonRequest
import kotlinx.android.synthetic.main.fragment_pesquisa_vendedores.*

private const val PESQUISA = "pesquisa"

class PesquisaVendedoresFragment : Fragment() {
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
        recyclerPesquisaVendedores.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        readVendedores()
    }

    private fun readVendedores() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/pesquisar/vendedores/q=$pesquisa"

        var request = GsonRequest(
            url, Array<VendedoresData>::class.java, null, Response.Listener { response ->
                var adapter =
                    RankingVendedoresAdapter(activity!!.baseContext, ArrayList(response.toList()))
                recyclerPesquisaVendedores.adapter = adapter

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
        return inflater.inflate(R.layout.fragment_pesquisa_vendedores, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            PesquisaVendedoresFragment().apply {
                arguments = Bundle().apply {
                    putString(PESQUISA, param1)
                }
            }
    }
}
