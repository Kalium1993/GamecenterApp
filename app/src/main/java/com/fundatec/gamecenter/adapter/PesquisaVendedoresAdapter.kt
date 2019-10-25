package com.fundatec.gamecenter.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fundatec.gamecenter.VendedorActivity
import com.fundatec.gamecenter.jsonData.VendedoresData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.vendedores_list.view.*

class PesquisaVendedoresAdapter (var context: Context, var vendedores: ArrayList<VendedoresData>)
    : androidx.recyclerview.widget.RecyclerView.Adapter<PesquisaVendedoresAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(com.fundatec.gamecenter.R.layout.vendedores_list, parent, false)
        return ViewHolder(v, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(vendedores[position], position)
    }


    override fun getItemCount(): Int {
        return vendedores.size
    }

    fun updateList(list:ArrayList<VendedoresData>){
        vendedores = list
        notifyDataSetChanged()

    }

    fun clearList(){
        vendedores.clear()
        notifyDataSetChanged()
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View, var ctx: Context) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: VendedoresData, i: Int) {
            var vendasTotais = item.vendas.toString().split(".")

            itemView.nickVendedorR.text = item.usuario!!.nick
            itemView.vendasVendedorR.text = "Vendas Realizadas: " + vendasTotais[0]
            itemView.notaVendedorR.text = "Nota: " + item.notaVendedor
            Picasso.get().load(item.usuario!!.foto).placeholder(com.fundatec.gamecenter.R.drawable.no_photo).fit().centerCrop()
                .into(itemView.fotoVendedorR)

            itemView.setOnClickListener { v ->
                val context = v.context
                val intent = Intent(context, VendedorActivity::class.java)
                intent.putExtra("nickVendedor", item.usuario!!.nick)
                context.startActivity(intent)
            }
        }

    }
}