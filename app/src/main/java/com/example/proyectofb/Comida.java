package com.example.proyectofb;

public class Comida {
    private String nombre, url, tamaño;
    private int precio;

    public Comida(String nombre, String url, String tamaño, int precio) {
        this.nombre = nombre;
        this.url = url;
        this.tamaño = tamaño;
        this.precio = precio;
    }

    public Comida(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "nombre: '" + nombre + '\'' +
                ", tamaño: '" + tamaño + '\'' +
                ", precio: " + precio +
                '}';
    }

    public String toString2(){
        return nombre;
    }
}
