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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "XYZ";

    EditText usuario, contraseña;
    Button log, registro;
    private ViewModel viewModelActivity;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        viewModelActivity = new ViewModelProvider(this).get(ViewModel.class);
        usuario = findViewById(R.id.etnombre);
        contraseña = findViewById(R.id.etcontraseña);
        log = findViewById(R.id.btlog);
        registro = findViewById(R.id.btreg);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // control de errores
                if (usuario.getText().toString().isEmpty()) {
                    usuario.setError("El correo no puede estar vacío");
                } else if (!usuario.getText().toString().contains("@")) {
                    usuario.setError("El correo debe contener un @");
                } else if (contraseña.getText().toString().isEmpty()) {
                    contraseña.setError("La contraseña no puede estar vacía");
                } else {
                    String nombre = usuario.getText().toString();
                    String contra = contraseña.getText().toString();
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                    firebaseAuth.signInWithEmailAndPassword(nombre, contra)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Log.v("XYZ", "success");
                                    currentUser = firebaseAuth.getCurrentUser();
                                    Log.v("XYZ", currentUser.getEmail() + " " + currentUser.isEmailVerified());
                                }
                            })
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.v("XYZ", "success");
                                    currentUser = firebaseAuth.getCurrentUser();
                                    if (task.getException() != null) {
                                        Log.v("XYZ", task.getException().toString());
                                        if (task.getException().toString().contains("password") || task.getException().toString().contains("no user") || task.getException().toString().contains("email")) {
                                            Toast toast = Toast.makeText(getApplicationContext(), " El usuario o la contraseña no son correctos ", Toast.LENGTH_LONG);
                                            toast.getView().setBackgroundColor(Color.RED);
                                            toast.show();
                                        }
                                        currentUser = null;
                                    } else {
                                        Log.v("XYZ", "No exception");
                                        currentUser = firebaseAuth.getCurrentUser();
                                    }
                                    if (currentUser == null) {
                                        Log.v("XYZ", "null");
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Usuario verificado", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, Acciones.class);
                                        intent.putExtra("nombre", nombre);
                                        intent.putExtra("contra", contra);
                                        startActivity(intent);
                                        Log.v("XYZ", currentUser.getEmail() + " " + currentUser.isEmailVerified());
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.v("XYZ", "failure");
                                }
                            });
                }
            }
        });



        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
            }
        });

    }
}