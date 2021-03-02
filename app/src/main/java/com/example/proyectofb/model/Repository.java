package com.example.proyectofb.model;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectofb.Comida;
import com.example.proyectofb.DBAcciones;
import com.example.proyectofb.Lista;
import com.example.proyectofb.Pedidos;
import com.example.proyectofb.Ventas;

import java.util.List;

public class Repository {
    DBAcciones acciones;

    public Repository(Context context) {
        this.acciones = new DBAcciones(context);
    }

    public boolean registrar(String nombre, String contra) {
        acciones.registrar(nombre,contra);
        return acciones.verificado2;
    }

    public boolean login(String nombre, String contra) {
        acciones.login(nombre,contra);
        return acciones.verificado;
    }

    public void insertar(Comida comi, String nombre11, String cominombre) {
        acciones.insertar(comi,nombre11,cominombre);
    }

    public void editar(Comida comi, String nombre11, String cominombre) {
        acciones.editar(comi,nombre11,cominombre);
    }

    public void eliminar(String nombre11, String nombrecomi) {
        acciones.eliminar(nombre11,nombrecomi);
    }

    public void mostrarComida(Lista v, String nombre11){
        acciones.mostrarComida(v,nombre11);
    }

    public void mostrarVentas(Pedidos v, String nombre11){
        acciones.mostrarVentas(v,nombre11);
    }

    public void vender(Ventas v,String nombre11,String nombrecomi){
        acciones.vender(v,nombre11,nombrecomi);
    }


}
