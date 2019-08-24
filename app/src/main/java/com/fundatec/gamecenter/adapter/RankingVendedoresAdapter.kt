package com.fundatec.gamecenter.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fundatec.gamecenter.ProdutosActivity
import com.fundatec.gamecenter.jsonData.VendedoresData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ranking_vendedores_list.view.*
import com.fundatec.gamecenter.VendedorActivity


class RankingVendedoresAdapter(var context: Context, var vendedores: ArrayList<VendedoresData>)
    : androidx.recyclerview.widget.RecyclerView.Adapter<RankingVendedoresAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(com.fundatec.gamecenter.R.layout.ranking_vendedores_list, parent, false)
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

            itemView.nickVendedorR.text = item.nick
            itemView.notaVendedorR.text = "Nota: " + item.notaVendedor.toString()
            Picasso.get().load(item.foto).placeholder(com.fundatec.gamecenter.R.drawable.no_photo).fit().centerCrop()
                .into(itemView.fotoVendedorR)

            if (i == 0) {
                Picasso.get().load(com.fundatec.gamecenter.R.drawable.top_badge).fit().centerCrop()
                    .into(itemView.topBadge)
            } else {
                Picasso.get().load(com.fundatec.gamecenter.R.drawable.abc_cab_background_internal_bg).fit().centerCrop()
                    .into(itemView.topBadge)
            }

            itemView.setOnClickListener { v ->
                val context = v.context
                val intent = Intent(context, VendedorActivity::class.java)
                intent.putExtra("nickVendedor", item.nick)
                context.startActivity(intent)
            }
        }

    }
}