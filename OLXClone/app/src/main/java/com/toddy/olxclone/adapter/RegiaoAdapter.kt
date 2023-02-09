package com.toddy.olxclone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toddy.olxclone.R

class RegiaoAdapter(
    private val regiaoList: MutableList<String>,
    private val OnClick: OnClickListener
) : RecyclerView.Adapter<RegiaoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRegiao: TextView

        init {
            tvRegiao = view.findViewById(R.id.tv_regiao)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.regiao_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val regiao = regiaoList[position]
        holder.tvRegiao.text = regiao

        holder.itemView.setOnClickListener {
            OnClick.onClickListener(regiao)
        }
    }

    override fun getItemCount(): Int {
        return regiaoList.size
    }

    interface OnClickListener {
        fun onClickListener(regiao: String)
    }
}