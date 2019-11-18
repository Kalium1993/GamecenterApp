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
import com.fundatec.gamecenter.shared.Logado
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_vendedor.*

private const val NICK_VENDEDOR = "nickVendedor"

class VendedorFragment : Fragment() {

    private var nickVendedor: String? = null
    private var queue : RequestQueue? = null
    private var nickLogado: String = ""
    private var idLogado: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nickVendedor = it.getString(NICK_VENDEDOR)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queue = Volley.newRequestQueue(activity?.baseContext)
        val logado = Logado(requireContext())
        idLogado = logado.getLogadoId()
        nickLogado = logado.getLogadoNick()
        readVendedor()
    }

    private fun readVendedor() {
        var url = "https://gamecenter-api.herokuapp.com/gamecenter/vendedor/$nickVendedor"

        val request = GsonJsonClassRequest(
            url,
            VendedoresData::class.java,
            Response.Listener { vendedor ->
                vendedorNick.text = vendedor.usuario!!.nick
                Picasso.get().load(vendedor.usuario!!.foto).placeholder(R.drawable.no_photo).fit().centerCrop().into(fotoVendedor)

                if (vendedor.usuario!!.nomeReal == null) {
                    nomeVendedor.text = ""
                } else {
                    nomeVendedor.text = "(${vendedor.usuario!!.nomeReal})"
                }

                if(vendedor.vendas!! > 0) {
                    var vendasTotais = vendedor.vendas.toString().split(".")

                    vendasVendedor.text = "Vendas Realizadas: " + vendasTotais[0]
                    notaVendedor.text = "Nota de Vendedor: " + vendedor.notaVendedor
                } else {
                    vendasVendedor.visibility = View.GONE
                    notaVendedor.text = "(Nenhuma venda efetuada)"
                }

                if(vendedor.usuario!!.nick == nickLogado)
                    anunciarProduto.visibility = View.VISIBLE

                anunciarProduto.setOnClickListener { v ->
                    val context =  v.context
                    val intent = Intent(context, ProdutoPostActivity::class.java)
                    intent.putExtra("nickVendedor", vendedorNick.text.toString())
                    context.startActivity(intent)
                }
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
            VendedorFragment().apply {
                arguments = Bundle().apply {
                    putString(NICK_VENDEDOR, param1)
                }
            }
    }

}

