package com.toddy.casaportemporada.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.toddy.casaportemporada.R
import com.toddy.casaportemporada.model.Anuncio

class AdapterAnuncios(
    private val anunciosList: MutableList<Anuncio>,
    private val onClick: OnClick
) : RecyclerView.Adapter<AdapterAnuncios.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgAnuncio: ImageView? = null
        var tvTitulo: TextView? = null
        var tvDescricao: TextView? = null
        var tvData: TextView? = null

        init {
            imgAnuncio = view.findViewById(R.id.iv_anuncio_item)
            tvTitulo = view.findViewById(R.id.tv_titulo)
            tvDescricao = view.findViewById(R.id.tv_descricao)
            tvData = view.findViewById(R.id.tv_data_hora)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anuncio, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val anuncio = anunciosList[position]

        Picasso.get().load(anuncio.urlImage).into(holder.imgAnuncio)

        holder.tvTitulo!!.text = anuncio.titulo
        holder.tvDescricao!!.text = anuncio.descricao
        holder.tvData!!.text = "data"

        holder.itemView.setOnClickListener {
           onClick.onClickListener(anuncio)
        }

    }

    override fun getItemCount(): Int {
        return anunciosList.size
    }

    interface OnClick {
        fun onClickListener(anuncio: Anuncio)
    }
}