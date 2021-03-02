package com.example.proyectofb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Acciones extends AppCompatActivity {
    private FirebaseUser currentUser;
    private static final String TAG = "XYZ";

    TextView welcome;
    Button btlistar, btadd , btdisc, btventas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acciones);
        init();
    }
    private FirebaseFirestore db;
    private void init() {
        db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        welcome = findViewById(R.id.tvbienvenida);
        String nombre = getIntent().getStringExtra("nombre");
        String contraseña = getIntent().getStringExtra("contra");

        firebaseAuth.signInWithEmailAndPassword(nombre, contraseña)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.v("XYZ","success");
                        currentUser = firebaseAuth.getCurrentUser();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.v(TAG,"success");
                        currentUser = firebaseAuth.getCurrentUser();

                        String email = currentUser.getEmail().toString();
                        String[] nombre1 = email.split("@");
                        String nombre11 = nombre1[0];
                        welcome.setText("Bienvenido/a " + nombre11);
                        if(task.getException() != null){
                            Log.v(TAG,task.getException().toString());
                            currentUser = null;
                        } else {
                            Log.v(TAG,"No exception");
                            currentUser = firebaseAuth.getCurrentUser();
                        }
                        if(currentUser == null){
                            Log.v(TAG,"null");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v(TAG,"failure");
                    }
                });

        btlistar = findViewById(R.id.btlistar);
        btadd = findViewById(R.id.btadd);
        btdisc = findViewById(R.id.btdisc);
        btventas = findViewById(R.id.btventas);

        btdisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Toast.makeText(getApplicationContext(),"Desconectando...",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Acciones.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Acciones.this, Add.class);
                intent.putExtra("nombre",nombre);
                intent.putExtra("contra",contraseña);
                startActivity(intent);
            }
        });

        btlistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Acciones.this, Lista.class);
                intent.putExtra("nombre",nombre);
                intent.putExtra("contra",contraseña);
                startActivity(intent);
            }
        });

        btventas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Acciones.this, Pedidos.class);
                intent.putExtra("nombre",nombre);
                intent.putExtra("contra",contraseña);
                startActivity(intent);
            }
        });
    }
}