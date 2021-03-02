package com.example.proyectofb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyectofb.viewmodel.ViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Item extends AppCompatActivity {
    Button btvolver, bteliminar,bteditar, btvender;
    ImageView iv;
    TextView tv;
    String nombrecomi,tamaño,foto,datos;
    int precio;
    private ViewModel viewModelActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        viewModelActivity = new ViewModelProvider(this).get(ViewModel.class);

        String nombre = getIntent().getStringExtra("nombre");
        String[] nombre1 = nombre.split("@");
        String nombre11 = nombre1[0];
        String contraseña = getIntent().getStringExtra("contra");

        tv = findViewById(R.id.tvfotoitem);
        iv = findViewById(R.id.fotofinal);

        btvolver = findViewById(R.id.btfoto);
        bteliminar = findViewById(R.id.bteliminari);
        bteditar = findViewById(R.id.bteditari);
        btvender = findViewById(R.id.btvenderi);


        Intent intent = getIntent();
        datos = intent.getStringExtra("comida");
        nombrecomi = intent.getStringExtra("nombrecomida");
        tamaño = intent.getStringExtra("tamaño");
        precio = intent.getIntExtra("precio",0);
        foto = intent.getStringExtra("imagen");

        btvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Item.this, Lista.class);
                intent.putExtra("nombre",nombre);
                intent.putExtra("contra",contraseña);
                startActivity(intent);
            }
        });

        bteliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModelActivity.eliminar(nombre11,nombrecomi);
                Toast.makeText(getApplicationContext(),nombrecomi + " eliminado/a correctamente",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Item.this, Lista.class);
                intent.putExtra("nombre",nombre);
                intent.putExtra("contra",contraseña);
                startActivity(intent);
            }
        });

        bteditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Item.this, Edit.class);
                intent.putExtra("nombre",nombre);
                intent.putExtra("contra",contraseña);
                intent.putExtra("nombrecomida",nombrecomi);
                intent.putExtra("tamaño",tamaño);
                intent.putExtra("preciocomida",precio);
                intent.putExtra("imagen", foto);
                startActivity(intent);
            }
        });

        btvender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ventas v = new Ventas(nombrecomi,foto,tamaño,precio);
                viewModelActivity.vender(v,nombre11,nombrecomi);
                Toast.makeText(view.getContext(),  nombrecomi + " vendido/a con éxito", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Item.this, Lista.class);
                intent.putExtra("nombre",nombre);
                intent.putExtra("contra",contraseña);
                startActivity(intent);
            }
        });


        tv.setText(datos);
        Glide.with(this).load(foto).into(iv);
    }
}