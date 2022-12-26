package com.example.controledeprodutos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterProdutosKoltin(private val produtosList: MutableList<ProdutoEntity>, private val onClickInterface:OnClick) :
    RecyclerView.Adapter<AdapterProdutosKoltin.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterProdutosKoltin.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_produto, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return produtosList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvProduto: TextView
        val tvEstoque: TextView
        val tvValor: TextView

        init {
            tvProduto = view.findViewById(R.id.tvProduto)
            tvEstoque = view.findViewById(R.id.tvEstoque)
            tvValor = view.findViewById(R.id.tvValor)
        }
    }

    interface OnClick{
        fun onClickListener(produto:ProdutoEntity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtosList[position]
        holder.tvProduto.text = produto.nome
        holder.tvEstoque.text = "Estoque: ${produto.estoque.toString()}"
        holder.tvValor.text = "$ ${produto.valor.toString()}"

        holder.itemView.setOnClickListener {
            onClickInterface.onClickListener(produto)
        }
    }
}