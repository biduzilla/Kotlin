package com.example.controledeprodutos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controledeprodutos.R;
import com.example.controledeprodutos.models.Produtos;

import java.util.List;

public class AdapterProdutos extends RecyclerView.Adapter<AdapterProdutos.MyViewHolder> {

    private List<Produtos> produtosList;

    public AdapterProdutos(List<Produtos> produtosList) {
        this.produtosList = produtosList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Produtos produtos = produtosList.get(position);
        holder.tvProduto.setText(produtos.getNome());
        holder.tvEstoque.setText(String.valueOf(produtos.getEstoque()));
        holder.tvValor.setText(String.valueOf(produtos.getValor()));
    }

    @Override
    public int getItemCount() {
        return produtosList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvProduto, tvEstoque, tvValor;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProduto = itemView.findViewById(R.id.tvProduto);
            tvEstoque = itemView.findViewById(R.id.tvEstoque);
            tvValor = itemView.findViewById(R.id.tvValor);
        }
    }
}
