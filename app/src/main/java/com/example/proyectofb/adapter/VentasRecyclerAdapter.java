package com.example.proyectofb.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.proyectofb.Lista;
import com.example.proyectofb.Ventas;

public class VentasRecyclerAdapter extends ListAdapter<Ventas, VentasViewHolder> {
    Lista l;
    public VentasRecyclerAdapter(@NonNull DiffUtil.ItemCallback<Ventas> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public VentasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return VentasViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull VentasViewHolder holder, int position) {
        Ventas current = getItem(position);
        if(!current.getUrl().contains("https://")){
            current.setUrl("https://i.imgur.com/aA1BoNb.png");
        }
        holder.bind(current.toString2(), current.getUrl());
    }

    public static class VentasDiff extends DiffUtil.ItemCallback<Ventas> {

        @Override
        public boolean areItemsTheSame(@NonNull Ventas oldItem, @NonNull Ventas newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Ventas oldItem, @NonNull Ventas newItem) {
            return oldItem.getNombre().equals(newItem.getNombre());
        }
    }
}
