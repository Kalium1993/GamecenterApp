package com.fundatec.gamecenter.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fundatec.gamecenter.R
import com.fundatec.gamecenter.UsuarioActivity
import com.fundatec.gamecenter.jsonData.UsuariosData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.usuarios_list.view.*

class UsuariosAdapter (var context: Context, var usuarios: ArrayList<UsuariosData>)
    : androidx.recyclerview.widget.RecyclerView.Adapter<UsuariosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.usuarios_list, parent, false)
        return ViewHolder(v, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(usuarios[position])
    }


    override fun getItemCount(): Int {
        return usuarios.size
    }

    fun updateList(list:ArrayList<UsuariosData>){
        usuarios = list
        notifyDataSetChanged()

    }

    fun clearList(){
        usuarios.clear()
        notifyDataSetChanged()
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View, var ctx: Context) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: UsuariosData) {

            itemView.nickUsuarioQ.text = item.nick

            if (item.nomeReal == null || item.nomeReal.isEmpty())
                itemView.nomeUsuarioQ.text = ""
            else
                itemView.nomeUsuarioQ.text = "(${item.nomeReal})"

            Picasso.get().load(item.foto).placeholder(R.drawable.no_photo).fit().centerCrop().into(itemView.fotoUsuarioQ)

            itemView.setOnClickListener { v ->
                val context = v.context
                val intent = Intent(context, UsuarioActivity::class.java)
                intent.putExtra("nick", item.nick)
                context.startActivity(intent)
            }

        }
    }
}