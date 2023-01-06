package com.toddy.gerenciadorreceitas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toddy.gerenciadorreceitas.R
import com.toddy.gerenciadorreceitas.models.Receita


class AdapterReceitas(private val receitasList: MutableList<Receita>, private val onClickInterface: OnClick) :
    RecyclerView.Adapter<AdapterReceitas.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_receita,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val receita = receitasList[position]
        holder.tvReceitaNome.text = receita.receita

        holder.itemView.setOnClickListener{
            onClickInterface.onClickListener(receita)
        }

    }

    override fun getItemCount(): Int {
        return receitasList.size
    }

    interface OnClick{
        fun onClickListener(receita: Receita)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tvReceitaNome:TextView
        init {
            tvReceitaNome = view.findViewById(R.id.tv_nome_receita)
        }
    }
}