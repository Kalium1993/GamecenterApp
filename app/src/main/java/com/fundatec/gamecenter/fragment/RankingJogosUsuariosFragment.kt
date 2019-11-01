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
import com.fundatec.gamecenter.adapter.RankingJogosAdapter
import com.fundatec.gamecenter.jsonData.JogosData
import com.fundatec.gamecenter.request.GsonRequest
import kotlinx.android.synthetic.main.fragment_ranking_jogos_usuarios.*

private const val ARG_PARAM1 = "param1"

class RankingJogosUsuariosFragment : Fragment() {
    private var param1: String? = null
    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queue = Volley.newRequestQueue(activity?.baseContext)
        recyclerRankingJogosUsuarios.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        readJogos()
    }

    private fun readJogos() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/jogos/ranking-usuarios"

        var request = GsonRequest(url, Array<JogosData>::class.java, null, Response.Listener { response ->
            var lista = ArrayList(response.toList())

            var adapter = RankingJogosAdapter(activity!!.baseContext, lista)
            recyclerRankingJogosUsuarios.adapter = adapter

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
        return inflater.inflate(R.layout.fragment_ranking_jogos_usuarios, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            RankingJogosUsuariosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}
