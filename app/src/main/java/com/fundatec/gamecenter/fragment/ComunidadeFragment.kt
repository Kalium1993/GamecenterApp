package com.fundatec.gamecenter.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.fundatec.gamecenter.LoginActivity
import com.fundatec.gamecenter.MainActivity

import com.fundatec.gamecenter.R
import com.fundatec.gamecenter.jsonData.ComunidadesData
import com.fundatec.gamecenter.request.GsonJsonClassRequest
import com.fundatec.gamecenter.request.GsonJsonRequest
import com.fundatec.gamecenter.shared.Logado
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_comunidade.*
import kotlinx.android.synthetic.main.fragment_topicos.*

private const val ID_COMUNIDADE = "idComunidade"

class ComunidadeFragment : Fragment() {
    private var idComunidade: String? = null
    private var queue : RequestQueue? = null
    private var nickLogado: String = ""
    private var idLogado: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idComunidade = it.getString(ID_COMUNIDADE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queue = Volley.newRequestQueue(activity?.baseContext)
        readComunidade()

        val logado = Logado(requireContext())
        idLogado = logado.getLogadoId()
        nickLogado = logado.getLogadoNick()

        deleteCmm.setOnClickListener {
            if(nickLogado.isEmpty() && idLogado.isEmpty()) {
                val alerta = AlertDialog.Builder(activity!!)
                alerta.setMessage("Você precisa efetuar login para deletar a comunidade.")
                alerta.setCancelable(false)
                alerta.setNegativeButton("Cancelar") { dialog, which ->

                }
                alerta.setPositiveButton("OK"){ dialog, which ->
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                }
                alerta.show()
            } else {
                val alerta = AlertDialog.Builder(activity!!)
                alerta.setMessage("Ao deletar a comunidade, todos os tópicos, e as mensagens dos mesmos, também serão excluídos, deseja continuar?")
                alerta.setCancelable(false)
                alerta.setNegativeButton("Cancelar") { dialog, which ->

                }
                alerta.setPositiveButton("Confirmar"){ dialog, which ->
                    deleteComuidade()
                }
                alerta.show()
            }
        }
    }

    private fun readComunidade() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/comunidade/$idComunidade"

        val request = GsonJsonClassRequest(
            url,
            ComunidadesData::class.java,
            Response.Listener { comunidade ->
                nomeCmm.text = comunidade.nome
                Picasso.get().load(comunidade.imagem).placeholder(R.drawable.no_img).fit().centerCrop().into(imagemCmm)
                descricaoCmm.text = comunidade.descricao
            },
            Response.ErrorListener { error ->
                Toast.makeText(activity?.baseContext, "" + error.message, Toast.LENGTH_SHORT).show()
            }
        )
        queue?.add(request)
    }

    private fun deleteComuidade() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/comunidade/$idComunidade/delete"

        var comunidade = ComunidadesData()
        var delete = Gson().toJson(comunidade)

        var request = GsonJsonRequest(Request.Method.DELETE, url, ComunidadesData::class.java, delete, Response.Listener { v ->
            val intent = Intent(activity?.baseContext, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(activity?.baseContext, "Comunidade Excluída", Toast.LENGTH_SHORT).show()
        }, Response.ErrorListener { e ->
            Toast.makeText(activity?.baseContext, "" + e.message, Toast.LENGTH_LONG).show()
        })

        queue?.add(request)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comunidade, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            ComunidadeFragment().apply {
                arguments = Bundle().apply {
                    putString(ID_COMUNIDADE, param1)
                }
            }
    }
}
