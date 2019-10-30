package com.fundatec.gamecenter.fragment

import android.content.Intent
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
import com.fundatec.gamecenter.TopicoPostActivity
import com.fundatec.gamecenter.adapter.TopicosAdapter
import com.fundatec.gamecenter.jsonData.TopicosData
import com.fundatec.gamecenter.request.GsonRequest
import kotlinx.android.synthetic.main.fragment_topicos.*

private const val ID_COMUNIDADE = "idComunidade"

class TopicosFragment : Fragment() {
    private var idComunidade: String? = null
    private var queue : RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idComunidade = it.getString(ID_COMUNIDADE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queue = Volley.newRequestQueue(activity?.baseContext)
        recyclerTopicos.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        readTopicos()
        postTopico()
    }

    private fun readTopicos() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/comunidade/$idComunidade/topicos"
        var request = GsonRequest(
            url, Array<TopicosData>::class.java, null, Response.Listener { response ->
                var adapter = TopicosAdapter(activity!!.baseContext, ArrayList(response.toList()))
                recyclerTopicos.adapter = adapter
            },
            Response.ErrorListener { error ->
                Toast.makeText(activity?.baseContext, "" + error.message, Toast.LENGTH_LONG).show()
            }
        )
        queue?.add(request)
    }

    private fun postTopico() {
        criarTopico.setOnClickListener { v ->
            val context =  v.context
            val intent = Intent(context, TopicoPostActivity::class.java)
            intent.putExtra("idComunidade", idComunidade)
            context.startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_topicos, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            TopicosFragment().apply {
                arguments = Bundle().apply {
                    putString(ID_COMUNIDADE, param1)
                }
            }
    }
}
