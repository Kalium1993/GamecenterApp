package com.fundatec.gamecenter.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.ComunidadeActivity
import com.fundatec.gamecenter.LoginActivity
import com.fundatec.gamecenter.R
import com.fundatec.gamecenter.TopicoPostActivity
import com.fundatec.gamecenter.adapter.TopicosAdapter
import com.fundatec.gamecenter.jsonData.TopicosData
import com.fundatec.gamecenter.request.GsonRequest
import com.fundatec.gamecenter.shared.Logado
import kotlinx.android.synthetic.main.fragment_topicos.*

private const val ID_COMUNIDADE = "idComunidade"
private const val PESQUISA = "pesquisa"

class TopicosFragment : Fragment() {
    private var idComunidade: String? = null
    private var pesquisa: String? = null
    private var queue : RequestQueue? = null
    private var nickLogado: String = ""
    private var idLogado: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idComunidade = it.getString(ID_COMUNIDADE)
            pesquisa = it.getString(PESQUISA)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queue = Volley.newRequestQueue(activity?.baseContext)
        recyclerTopicos.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val logado = Logado(requireContext())
        idLogado = logado.getLogadoId()
        nickLogado = logado.getLogadoNick()

        readTopicos()
        postTopico()
        pesquisa()
    }

    private fun readTopicos() {
        var url = if(pesquisa!!.isEmpty())
            "https://gamecenter-api.herokuapp.com/gamecenter/comunidade/$idComunidade/topicos"
        else
            "https://gamecenter-api.herokuapp.com/gamecenter/comunidade/$idComunidade/pesquisar/topicos/q=$pesquisa"

        var request = GsonRequest(url, Array<TopicosData>::class.java, null, Response.Listener { response ->
                var lista = ArrayList(response.toList())
                if(lista.isEmpty() && pesquisa!!.isNotEmpty()) {
                    recyclerTopicos.visibility = View.GONE
                    emptyTopicos.visibility = View.VISIBLE
                    emptyTopicos.text = "Não foram encontrados resultados para a pesquisa: '$pesquisa'"
                } else if (lista.isEmpty() && pesquisa!!.isEmpty()) {
                    recyclerTopicos.visibility = View.GONE
                    emptyTopicos.visibility = View.VISIBLE
                }


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
            if(nickLogado.isEmpty() && idLogado.isEmpty()) {
                val alerta = AlertDialog.Builder(activity!!)
                alerta.setMessage("Você precisa efetuar login para criar um tópico.")
                alerta.setCancelable(false)
                alerta.setNegativeButton("Cancelar") { dialog, which ->

                }
                alerta.setPositiveButton("OK"){ dialog, which ->
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                }
                alerta.show()
            } else {
                val context =  v.context
                val intent = Intent(context, TopicoPostActivity::class.java)
                intent.putExtra("idComunidade", idComunidade)
                context.startActivity(intent)
            }
        }
    }

    private fun pesquisa() {
        searchTopicos.setOnSearchClickListener {
            criarTopico.visibility = View.GONE
        }

        searchTopicos.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                criarTopico.visibility = View.VISIBLE
                return false
            }
        })

        searchTopicos.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(pesquisa: String): Boolean {
                val intent = Intent(activity?.baseContext, ComunidadeActivity::class.java)
                intent.putExtra("idComunidade", idComunidade)
                intent.putExtra("pesquisa", pesquisa)
                startActivity(intent)

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // do something when text changes
                return false
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_topicos, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(idComunidade: String, pesquisa: String) =
            TopicosFragment().apply {
                arguments = Bundle().apply {
                    putString(ID_COMUNIDADE, idComunidade)
                    putString(PESQUISA, pesquisa)
                }
            }
    }
}
