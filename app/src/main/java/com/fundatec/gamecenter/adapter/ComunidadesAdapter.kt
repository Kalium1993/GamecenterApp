package com.fundatec.gamecenter.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fundatec.gamecenter.ComunidadeActivity2
import com.fundatec.gamecenter.R
import com.fundatec.gamecenter.jsonData.ComunidadesData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.comunidades_list.view.*

class ComunidadesAdapter(var context: Context, var comunidades: ArrayList<ComunidadesData>)
    : androidx.recyclerview.widget.RecyclerView.Adapter<ComunidadesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.comunidades_list, parent, false)
        return ViewHolder(v, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(comunidades[position])
    }


    override fun getItemCount(): Int {
        return comunidades.size
    }

    fun updateList(list:ArrayList<ComunidadesData>){
        comunidades = list
        notifyDataSetChanged()

    }

    fun clearList(){
        comunidades.clear()
        notifyDataSetChanged()
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View, var ctx: Context) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: ComunidadesData) {

            itemView.nomeComunidade.text = item.nome
            itemView.descricaoComunidade.text = item.descricao
            Picasso.get().load(item.imagem).placeholder(R.drawable.no_img).fit().centerCrop().into(itemView.imagemComunidade)

            itemView.setOnClickListener { v ->
                val context = v.context
                val intent = Intent(context, ComunidadeActivity2::class.java)
                intent.putExtra("idComunidade", item.id)
                context.startActivity(intent)
            }

        }
    }
}