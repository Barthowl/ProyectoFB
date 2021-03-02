package com.example.proyectofb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.proyectofb.adapter.ComidaRecyclerAdapter;
import com.example.proyectofb.viewmodel.ViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Lista extends AppCompatActivity {

    Button volver;
    RecyclerView rvlista;
    ComidaRecyclerAdapter adapter;
    private ViewModel viewModelActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelActivity = new ViewModelProvider(this).get(ViewModel.class);
        setContentView(R.layout.activity_lista);
        init();
    }

    private void init() {
        volver = findViewById(R.id.btvolverlista);
        String nombre = getIntent().getStringExtra("nombre");
        String contraseña = getIntent().getStringExtra("contra");
        String[] nombre1 = nombre.split("@");
        String nombre11 = nombre1[0];
        viewModelActivity.mostrarComida(this,nombre11);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lista.this, Acciones.class);
                intent.putExtra("nombre",nombre);
                intent.putExtra("contra",contraseña);
                startActivity(intent);
            }
        });

    }

    public void irItem(Comida c){
        Intent intent = new Intent(Lista.this, Item.class);
        String datos = "Nombre: " + c.getNombre() + " \n Tamaño:" + c.getTamaño() + " \n Precio: " + c.getPrecio() + " €";
        String nombre = getIntent().getStringExtra("nombre");
        String contraseña = getIntent().getStringExtra("contra");
        intent.putExtra("nombre",nombre);
        intent.putExtra("contra",contraseña);
        intent.putExtra("comida",datos);
        intent.putExtra("imagen",c.getUrl());
        intent.putExtra("nombrecomida",c.getNombre());
        intent.putExtra("tamaño",c.getTamaño());
        intent.putExtra("precio",c.getPrecio());
        startActivity(intent);
    }
}