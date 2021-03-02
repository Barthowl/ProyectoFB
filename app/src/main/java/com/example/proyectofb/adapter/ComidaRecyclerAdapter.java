package com.example.proyectofb.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.proyectofb.Comida;
import com.example.proyectofb.Lista;

public class ComidaRecyclerAdapter extends ListAdapter<Comida, ComidaViewHolder>  {
    Lista l;
    public ComidaRecyclerAdapter(@NonNull DiffUtil.ItemCallback<Comida> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ComidaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ComidaViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ComidaViewHolder holder, int position) {
        Comida current = getItem(position);
        if(!current.getUrl().contains("https://")){
            current.setUrl("https://i.imgur.com/aA1BoNb.png");
        }
        holder.bind(current.toString2(), current.getUrl());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l = (Lista) view.getContext();
                l.irItem(current);
            }
        });
    }

    public static class ComidaDiff extends DiffUtil.ItemCallback<Comida> {

        @Override
        public boolean areItemsTheSame(@NonNull Comida oldItem, @NonNull Comida newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Comida oldItem, @NonNull Comida newItem) {
            return oldItem.getNombre().equals(newItem.getNombre());
        }
    }
}
