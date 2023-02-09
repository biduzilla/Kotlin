package com.toddy.olxclone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toddy.olxclone.R
import com.toddy.olxclone.model.Estado

class EstadoAdapter(
    private val estadoList: MutableList<Estado>,
    private val OnClick: OnClickListener
) : RecyclerView.Adapter<EstadoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvEstado: TextView

        init {
            tvEstado = view.findViewById(R.id.tv_estado)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.estado_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val estado = estadoList[position]
        holder.tvEstado.text = estado.nome

        holder.itemView.setOnClickListener {
            OnClick.onClickListener(estado)
        }
    }

    override fun getItemCount(): Int {
        return estadoList.size
    }

    interface OnClickListener {
        fun onClickListener(estado: Estado)
    }
}