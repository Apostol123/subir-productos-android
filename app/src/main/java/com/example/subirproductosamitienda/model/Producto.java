package com.example.subirproductosamitienda.model;

import java.util.ArrayList;

public class Producto {
    private String nombre;
    private String descripcion;
    private String url_img;
    private int precio;
    private ArrayList<String> atributos;

    public Producto(String nombre, String descripcion, String url_img, int precio, ArrayList<String> atributos) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.url_img = url_img;
        this.precio = precio;
        this.atributos = atributos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public ArrayList<String> getAtributos() {
        return atributos;
    }

    public void setAtributos(ArrayList<String> atributos) {
        this.atributos = atributos;
    }
}
