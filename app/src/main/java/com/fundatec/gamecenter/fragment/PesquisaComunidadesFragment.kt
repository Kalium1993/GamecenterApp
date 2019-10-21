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
import com.fundatec.gamecenter.adapter.ComunidadesAdapter
import com.fundatec.gamecenter.jsonData.ComunidadesData
import com.fundatec.gamecenter.request.GsonRequest
import kotlinx.android.synthetic.main.fragment_pesquisa_comunidades.*

private const val PESQUISA = "pesquisa"

class PesquisaComunidadesFragment : Fragment() {
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
        recyclerPesquisaComunidades.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        readComunidades()
    }

    private fun readComunidades() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/pesquisar/comunidades/q=$pesquisa"

        var request = GsonRequest(
            url, Array<ComunidadesData>::class.java, null, Response.Listener { response ->
                var adapter =
                    ComunidadesAdapter(activity!!.baseContext, ArrayList(response.toList()))
                recyclerPesquisaComunidades.adapter = adapter

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
        return inflater.inflate(R.layout.fragment_pesquisa_comunidades, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            PesquisaComunidadesFragment().apply {
                arguments = Bundle().apply {
                    putString(PESQUISA, param1)
                }
            }
    }
}
