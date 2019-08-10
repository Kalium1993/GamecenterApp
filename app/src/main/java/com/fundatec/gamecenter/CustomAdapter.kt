package com.fundatec.gamecenter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_list.view.*
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity




class CustomAdapter(var context: Context, var news: ArrayList<Article>) : androidx.recyclerview.widget.RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.news_list, parent, false)
        return ViewHolder(v, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(news[position])
    }


    override fun getItemCount(): Int {
        return news.size
    }

    fun updateList(list:ArrayList<Article>){
        news = list
        notifyDataSetChanged()

    }

    fun clearList(){
        news.clear()
        notifyDataSetChanged()
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View, var ctx: Context) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: Article) {

            itemView.newsTitle.text = item.title
            itemView.newsDescription.text = item.description
            Picasso.get().load(item.urlToImage).placeholder(R.drawable.no_img).fit()   .centerCrop().into(itemView.newsImg)

            itemView.setOnClickListener {

                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(item.url)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(i)
            }
        }
    }
}