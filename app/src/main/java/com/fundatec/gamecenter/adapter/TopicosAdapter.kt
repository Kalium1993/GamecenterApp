package com.fundatec.gamecenter.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fundatec.gamecenter.R
import com.fundatec.gamecenter.TopicoActivity
import com.fundatec.gamecenter.jsonData.TopicosData
import kotlinx.android.synthetic.main.topicos_list.view.*

class TopicosAdapter (var context: Context, var topicos: ArrayList<TopicosData>)
    : androidx.recyclerview.widget.RecyclerView.Adapter<TopicosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.topicos_list, parent, false)
        return ViewHolder(v, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(topicos[position])
    }


    override fun getItemCount(): Int {
        return topicos.size
    }

    fun updateList(list:ArrayList<TopicosData>){
        topicos = list
        notifyDataSetChanged()

    }

    fun clearList(){
        topicos.clear()
        notifyDataSetChanged()
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View, var ctx: Context) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: TopicosData) {

            itemView.topico.text = item.titulo

            itemView.setOnClickListener { v ->
                val context = v.context
                val intent = Intent(context, TopicoActivity::class.java)
                intent.putExtra("idComunidade", item.comunidade)
                intent.putExtra("idTopico", item.id)
                context.startActivity(intent)
            }

        }
    }
}