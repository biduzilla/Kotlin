package com.toddy.olxclone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.toddy.olxclone.R
import com.toddy.olxclone.helper.GetMask
import com.toddy.olxclone.model.Anuncio

class AnuncioAdapter(private val anuncioList:MutableList<Anuncio>, private val OnClick:OnClickListener)  :RecyclerView.Adapter<AnuncioAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.anuncio_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return anuncioList.size
    }

    interface OnClickListener {
        fun OnClick(anuncio: Anuncio)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgAnuncios: ImageView
        val tvTitulo: TextView
        val tvPreco: TextView
        val tvLocal: TextView

        init {
            imgAnuncios = view.findViewById(R.id.img_anuncio)
            tvTitulo = view.findViewById(R.id.tvTitulo)
            tvPreco = view.findViewById(R.id.tv_preco)
            tvLocal = view.findViewById(R.id.tv_local)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val anuncio = anuncioList[position]
        Picasso.get().load(anuncio.imagens[0]).into(holder.imgAnuncios)
        holder.tvTitulo.text = anuncio.titulo
        holder.tvPreco.text = "R$ ${GetMask.getValor(anuncio.valor!!)}"
        holder.tvLocal.text =
            "${GetMask.getDate(anuncio.dataPublicacao!!, 4)}, ${anuncio.local!!.bairro}"

        holder.itemView.setOnClickListener {
            OnClick.OnClick(anuncio)
        }
    }
}