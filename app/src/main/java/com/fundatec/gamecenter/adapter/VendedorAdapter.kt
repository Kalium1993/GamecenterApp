package com.fundatec.gamecenter.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fundatec.gamecenter.ProdutoActivity
import com.fundatec.gamecenter.R
import com.fundatec.gamecenter.jsonData.ProdutosData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.produtos_list.view.*
import kotlinx.android.synthetic.main.produtos_vendedor.view.*

class VendedorAdapter(var context: Context, var produtos: ArrayList<ProdutosData>)
    : androidx.recyclerview.widget.RecyclerView.Adapter<VendedorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.produtos_vendedor, parent, false)
        return ViewHolder(v, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(produtos[position])
    }


    override fun getItemCount(): Int {
        return produtos.size
    }

    fun updateList(list:ArrayList<ProdutosData>){
        produtos = list
        notifyDataSetChanged()

    }

    fun clearList(){
        produtos.clear()
        notifyDataSetChanged()
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View, var ctx: Context) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: ProdutosData) {

            itemView.nomeProdutoVendedor.text = item.nome
            Picasso.get().load(item.imagem).placeholder(R.drawable.no_img).fit().centerCrop().into(itemView.imagemProdutoVendedor)
            if (!item.vendido) {
                itemView.descricaoProdutoVendedor.text = item.descricao
                itemView.notaVenda.text = ""
                itemView.vendido.text = "Produto á Venda!"
                itemView.cmmComprador.text = ""

                itemView.setOnClickListener { v ->
                    val context = v.context
                    val intent = Intent(context, ProdutoActivity::class.java)
                    intent.putExtra("nickVendedor", item.nickVendedor)
                    intent.putExtra("idProduto", item.id)
                    context.startActivity(intent)
                }

            } else {
                itemView.descricaoProdutoVendedor.text = item.cmmComprador
                itemView.notaVenda.text = "Nota da Venda: " + item.notaVenda
                itemView.vendido.text = "Produto Vendido"
            }
        }
    }

}