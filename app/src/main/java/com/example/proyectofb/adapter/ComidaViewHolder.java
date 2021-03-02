package com.example.proyectofb.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectofb.R;

public class ComidaViewHolder extends RecyclerView.ViewHolder {

    private final ImageView foto;
    private final TextView nombre;
    public final LinearLayout layout;
    private final View view;


    public ComidaViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        this.foto=itemView.findViewById(R.id.comiimg);
        this.nombre=itemView.findViewById(R.id.cominombre);
        this.layout=itemView.findViewById(R.id.parent_layout);
    }

    @SuppressLint("ResourceType")
    public void bind(String text,String comida){
        nombre.setText(text);
        Glide.with(view.getContext()).load(comida).into(foto);
    }

    public static ComidaViewHolder create(ViewGroup parent){
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item,parent,false);
        return new ComidaViewHolder(view);
    }

}
