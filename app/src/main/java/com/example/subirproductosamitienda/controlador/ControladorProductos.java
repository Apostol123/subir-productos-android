package com.example.subirproductosamitienda.controlador;

import com.example.subirproductosamitienda.model.Producto;
import com.example.subirproductosamitienda.vista.LoginScreen;

public class ControladorProductos {
    private Producto producto;
    private LoginScreen vista;

    public ControladorProductos(Producto producto, LoginScreen vista) {
        this.producto = producto;
        this.vista = vista;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public LoginScreen getVista() {
        return vista;
    }

    public void setVista(LoginScreen vista) {
        this.vista = vista;
    }
}
