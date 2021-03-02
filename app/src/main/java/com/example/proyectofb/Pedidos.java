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

import com.example.proyectofb.adapter.VentasRecyclerAdapter;
import com.example.proyectofb.viewmodel.ViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Pedidos extends AppCompatActivity {

    Button btvolver;
    private ViewModel viewModelActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelActivity = new ViewModelProvider(this).get(ViewModel.class);
        setContentView(R.layout.activity_pedidos);
        String nombre = getIntent().getStringExtra("nombre");
        String contraseña = getIntent().getStringExtra("contra");

        String[] nombre1 = nombre.split("@");
        String nombre11 = nombre1[0];
        viewModelActivity.mostrarVentas(this, nombre11);


        btvolver = findViewById(R.id.btvolverventas);

        btvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pedidos.this, Acciones.class);
                intent.putExtra("nombre",nombre);
                intent.putExtra("contra",contraseña);
                startActivity(intent);
            }
        });

    }
}