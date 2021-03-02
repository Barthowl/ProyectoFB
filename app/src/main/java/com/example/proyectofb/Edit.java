package com.example.proyectofb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectofb.viewmodel.ViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Edit extends AppCompatActivity {

    Button btvolver, btsig;
    EditText nombrecom , tamaño , imagen , precio;
    private ViewModel viewModelActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelActivity = new ViewModelProvider(this).get(ViewModel.class);

        setContentView(R.layout.activity_edit);
        String nombre = getIntent().getStringExtra("nombre");
        String contraseña = getIntent().getStringExtra("contra");

        String[] nombre1 = nombre.split("@");
        String nombre11 = nombre1[0];

        String nombrecomi = getIntent().getStringExtra("nombrecomida");
        String tamañocomi = getIntent().getStringExtra("tamaño");
        int preciocomi = getIntent().getIntExtra("preciocomida",0);
        String foto = getIntent().getStringExtra("imagen");
        Log.v("XYZ","precio: " + preciocomi);
        nombrecom = findViewById(R.id.etnombree);
        tamaño = findViewById(R.id.ettamañoe);
        imagen = findViewById(R.id.etimagene);
        precio = findViewById(R.id.etprecioe);

        nombrecom.setText(nombrecomi);
        tamaño.setText(tamañocomi);
        precio.setText(String.valueOf(preciocomi));
        imagen.setText(foto);

        btvolver = findViewById(R.id.btvolveredit);
        btsig = findViewById(R.id.bteditar);

        btvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Edit.this, Lista.class);
                intent.putExtra("nombre",nombre);
                intent.putExtra("contra",contraseña);
                startActivity(intent);
            }
        });

        btsig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // control de errores
                if(nombrecom.getText().toString().isEmpty()){
                    nombrecom.setError("El nombre no puede quedar vacío");
                } else if(tamaño.getText().toString().isEmpty()){
                    tamaño.setError("El tamaño no puede quedar vacío");
                } else if(imagen.getText().toString().isEmpty()){
                    imagen.setError("La imagen no puede quedar vacía");
                }  else if(precio.getText().toString().isEmpty()){
                    precio.setError("El precio no puede quedar vacío");
                } else {
                    String cominombre = nombrecom.getText().toString();
                    String comitamaño = tamaño.getText().toString();
                    String comiimagen = imagen.getText().toString();
                    int comiprecio = Integer.valueOf(precio.getText().toString());

                    Comida comi = new Comida(cominombre, comiimagen, comitamaño, comiprecio);
                    viewModelActivity.editar(comi,nombre11,cominombre);
                    Toast.makeText(view.getContext(),  cominombre + " editado/a con éxito", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Edit.this, Lista.class);
                    intent.putExtra("nombre",nombre);
                    intent.putExtra("contra",contraseña);
                    startActivity(intent);

                }
            }
        });
    }
}