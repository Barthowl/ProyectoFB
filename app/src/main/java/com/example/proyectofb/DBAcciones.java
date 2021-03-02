package com.example.proyectofb;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofb.adapter.ComidaRecyclerAdapter;
import com.example.proyectofb.adapter.VentasRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class DBAcciones {
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    Context context;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    RecyclerView rvlista;
    ComidaRecyclerAdapter adapter;
    public boolean verificado;
    public boolean verificado2;

    RecyclerView rvlista2;
    VentasRecyclerAdapter adapter2;

    public DBAcciones(Context context) {
        this.context = context.getApplicationContext();
    }

    public boolean registrar(String nombre, String contra){
        firebaseAuth.createUserWithEmailAndPassword(nombre, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.getException()!=null) {
                    Log.v("XYZ", task.getException().toString());
                    if(task.getException().toString().contains("email")){
                        Toast toast = Toast.makeText(context," Existe una cuenta con ese email, escoge otro ", Toast.LENGTH_LONG);
                        verificado2 = false;
                        toast.getView().setBackgroundColor(Color.RED);
                        toast.show();
                    }
                } else {
                    verificado2 = true;
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Log.v("XYZ","success");
                verificado2 = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.v("XYZ","failure");
                verificado2 = false;
            }
        });
        return verificado2;
    }

    public boolean login(String nombre, String contra){
        firebaseAuth.signInWithEmailAndPassword(nombre, contra)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.v("XYZ","success");
                        currentUser = firebaseAuth.getCurrentUser();
                        Log.v("XYZ",currentUser.getEmail() + " " + currentUser.isEmailVerified());
                        verificado = true;
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.v("XYZ","success");
                        currentUser = firebaseAuth.getCurrentUser();
                        if(task.getException() != null){
                            Log.v("XYZ",task.getException().toString());
                            if(task.getException().toString().contains("password") || task.getException().toString().contains("no user") || task.getException().toString().contains("email")){
                                Toast toast = Toast.makeText(context," El usuario o la contraseña no son correctos ", Toast.LENGTH_LONG);
                                toast.getView().setBackgroundColor(Color.RED);
                                verificado = false;
                                toast.show();
                            }
                            currentUser = null;
                        } else {
                            Log.v("XYZ","No exception");
                            currentUser = firebaseAuth.getCurrentUser();
                        }
                        if(currentUser == null){
                            Log.v("XYZ","null");
                        } else {
                            Toast.makeText(context,"Usuario verificado",Toast.LENGTH_SHORT).show();
                            verificado = true;
                            Log.v("XYZ",currentUser.getEmail() + " " + currentUser.isEmailVerified());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v("XYZ","failure");
                    }
                });
        return verificado;
    }

    public void insertar(Comida comi,String nombre11, String cominombre){
        db = FirebaseFirestore.getInstance();
        Log.v("XYZ", nombre11 + " " + cominombre);
        CollectionReference collection = db.collection("user/" + nombre11 + "/comidas");
        collection.document(cominombre).set(comi).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }


        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.v("XYZ", e.toString());
            }
        });
    }

    public void eliminar(String nombre11, String nombrecomi){
        db = FirebaseFirestore.getInstance();
        db.collection("user/" + nombre11 + "/comidas").document(nombrecomi).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.v("XYZ", e.toString());
            }
        });
    }

    public void editar(Comida comi ,String nombre11,String nombrecomi){
        db = FirebaseFirestore.getInstance();
        db.collection("user/" + nombre11 + "/comidas").document(nombrecomi).set(comi).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.v("XYZ","failure");
            }
        });
    }

    public void mostrarComida(Lista v, String nombre11){
        db = FirebaseFirestore.getInstance();
        db.collection("user/" + nombre11 + "/comidas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Comida> comi = task.getResult().toObjects(Comida.class);
                            Log.v("XYZ",comi.toString());

                            rvlista = v.findViewById(R.id.rv_comida);
                            rvlista.setHasFixedSize(true);

                            adapter = new ComidaRecyclerAdapter(new ComidaRecyclerAdapter.ComidaDiff());
                            adapter.submitList(comi);

                            rvlista.setLayoutManager(new LinearLayoutManager(context));
                            rvlista.setAdapter(adapter);

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.v("XYZ", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.v("XYZ", task.getException().toString());
                        }
                    }
                });
    }

    public void mostrarVentas(Pedidos v, String nombre11){
        db = FirebaseFirestore.getInstance();
        db.collection("user/" + nombre11 + "/ventas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Ventas> ventas = task.getResult().toObjects(Ventas.class);

                            rvlista2 = v.findViewById(R.id.rv_ventas);
                            rvlista2.setHasFixedSize(true);

                            adapter2 = new VentasRecyclerAdapter(new VentasRecyclerAdapter.VentasDiff());
                            adapter2.submitList(ventas);

                            rvlista2.setLayoutManager(new LinearLayoutManager(context));
                            rvlista2.setAdapter(adapter2);

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.v("XYZ", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.v("XYZ", task.getException().toString());
                        }
                    }
                });
    }

    public void vender(Ventas v,String nombre11,String nombrecomi){
        db = FirebaseFirestore.getInstance();
        CollectionReference collection = db.collection("user/" + nombre11+"/ventas");
        collection.document().set(v).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }


        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.v("XYZ", e.toString());
            }
        });
    }


}
