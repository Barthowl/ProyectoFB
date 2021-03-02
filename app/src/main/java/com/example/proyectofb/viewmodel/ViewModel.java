package com.example.proyectofb.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.proyectofb.Comida;
import com.example.proyectofb.Lista;
import com.example.proyectofb.Pedidos;
import com.example.proyectofb.Ventas;
import com.example.proyectofb.model.Repository;

public class ViewModel extends AndroidViewModel  {
    private Repository repository;

    public ViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public boolean registrar(String nombre, String contra) {
       return repository.registrar(nombre,contra);
    }

    public boolean login(String nombre, String contra) {
        return repository.login(nombre,contra);
    }

    public void insertar(Comida comi, String nombre11, String cominombre) {
        repository.insertar(comi,nombre11,cominombre);
    }

    public void editar(Comida comi, String nombre11, String cominombre) {
        repository.editar(comi,nombre11,cominombre);
    }

    public void eliminar(String nombre11, String nombrecomi) {
        repository.eliminar(nombre11,nombrecomi);
    }

    public void mostrarComida(Lista v, String nombre11){
        repository.mostrarComida(v,nombre11);
    }

    public void mostrarVentas(Pedidos v, String nombre11){
        repository.mostrarVentas(v,nombre11);
    }

    public void vender(Ventas v, String nombre11, String nombrecomi){
        repository.vender(v,nombre11,nombrecomi);
    }
}
