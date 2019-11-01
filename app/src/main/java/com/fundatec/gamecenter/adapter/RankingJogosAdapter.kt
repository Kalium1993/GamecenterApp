package com.fundatec.gamecenter.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fundatec.gamecenter.JogoActivity
import com.fundatec.gamecenter.R
import com.fundatec.gamecenter.jsonData.JogosData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.ranking_jogos.view.*

class RankingJogosAdapter (var context: Context, var jogos: ArrayList<JogosData>)
    : androidx.recyclerview.widget.RecyclerView.Adapter<RankingJogosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.ranking_jogos, parent, false)
        return ViewHolder(v, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(jogos[position], position, jogos.size)
    }


    override fun getItemCount(): Int {
        return jogos.size
    }

    fun updateList(list:ArrayList<JogosData>){
        jogos = list
        notifyDataSetChanged()

    }

    fun clearList(){
        jogos.clear()
        notifyDataSetChanged()
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View, var ctx: Context) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: JogosData, i: Int, size: Int) {
            itemView.jogoTituloR.text = item.titulo
            itemView.jogoNotaMidiaR.text = "Nota da Midia: " +item.notaMidia
            itemView.jogoNotaUsuariosR.text = "Nota dos Usuários: " +item.notaUsuarios


            Picasso.get().load(item.foto).placeholder(R.drawable.no_img).fit().centerCrop().into(itemView.jogoFotoR)

            itemView.setOnClickListener { v ->
                 val context = v.context
                 val intent = Intent(context, JogoActivity::class.java)
                 intent.putExtra("idJogo", item.id)
                 context.startActivity(intent)
            }
        }
    }

}