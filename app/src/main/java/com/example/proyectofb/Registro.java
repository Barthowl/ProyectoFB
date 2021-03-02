package com.example.proyectofb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectofb.viewmodel.ViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Registro extends AppCompatActivity {

    EditText usuario, contraseña;
    Button volver, registro;
    private ViewModel viewModelActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        init();
    }

    private void init() {
        viewModelActivity = new ViewModelProvider(this).get(ViewModel.class);

        usuario = findViewById(R.id.etusuarior);
        contraseña = findViewById(R.id.etcontraseñar);
        volver = findViewById(R.id.btvolverr);
        registro = findViewById(R.id.btsigr);

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, MainActivity.class);
                startActivity(intent);
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // control de errores
                if(usuario.getText().toString().isEmpty()){
                    usuario.setError("El correo no puede estar vacío");
                }  else if (!usuario.getText().toString().contains("@")){
                    usuario.setError("El correo debe contener un @");
                }else if (contraseña.getText().toString().isEmpty()){
                    contraseña.setError("La contraseña no puede estar vacía");
                } else if(contraseña.getText().toString().length() < 6){
                    contraseña.setError("La contraseña debe tener al menos 6 carácteres");
                } else {
                    String nombre = usuario.getText().toString();
                    String contra = contraseña.getText().toString();
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.createUserWithEmailAndPassword(nombre, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.getException()!=null) {
                                Log.v("XYZ", task.getException().toString());
                                if(task.getException().toString().contains("email")){
                                    Toast toast = Toast.makeText(getApplicationContext()," Existe una cuenta con ese email, escoge otro ", Toast.LENGTH_LONG);
                                    toast.getView().setBackgroundColor(Color.RED);
                                    toast.show();
                                }
                            }
                        }
                    }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Log.v("XYZ","success");
                            Toast.makeText(getApplicationContext(), "Usuario creado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Registro.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.v("XYZ","failure");
                        }
                    });
                    }

                }
            });
        }
    }

