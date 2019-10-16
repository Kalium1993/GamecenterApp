package com.fundatec.gamecenter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fundatec.gamecenter.R
import com.fundatec.gamecenter.jsonData.MensagensData
import kotlinx.android.synthetic.main.mensagens_topico.view.*

class MensagensAdapter(var context: Context, var mensagens: ArrayList<MensagensData>)
    : androidx.recyclerview.widget.RecyclerView.Adapter<MensagensAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.mensagens_topico, parent, false)
        return ViewHolder(v, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(mensagens[position])
    }


    override fun getItemCount(): Int {
        return mensagens.size
    }

    fun updateList(list:ArrayList<MensagensData>){
        mensagens = list
        notifyDataSetChanged()

    }

    fun clearList(){
        mensagens.clear()
        notifyDataSetChanged()
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View, var ctx: Context) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: MensagensData) {
            itemView.mensagem.text = item.mensagem

        }
    }
}