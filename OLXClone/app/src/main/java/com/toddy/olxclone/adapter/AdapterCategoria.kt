package com.toddy.olxclone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toddy.olxclone.R
import com.toddy.olxclone.model.Categoria

class AdapterCategoria(
    private val categoriaList: MutableList<Categoria>,
    private val OnClick: OnClickListener
) :
    RecyclerView.Adapter<AdapterCategoria.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.categoria_item, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return categoriaList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgCategoria: ImageView
        val textCategoria: TextView

        init {
            imgCategoria = view.findViewById(R.id.img_categoria)
            textCategoria = view.findViewById(R.id.text_categoria)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoria = Categoria()
        categoria.caminho?.let { holder.imgCategoria.setImageResource(it) }
        categoria.nome?.let { holder.textCategoria.text }

        holder.itemView.setOnClickListener {
            OnClick.OnClick(categoria)
        }
    }

    interface OnClickListener {
        fun OnClick(categoria: Categoria)
    }
}