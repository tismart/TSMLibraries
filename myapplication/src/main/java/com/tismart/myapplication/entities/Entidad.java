package com.tismart.myapplication.entities;

import com.tismart.tsmlibrary.database.annotations.Elemento;
import com.tismart.tsmlibrary.database.enums.TipoElemento;

/**
 * Created by luis.burgos on 07/05/2015.
 * 
 */
@SuppressWarnings("unused")
@com.tismart.tsmlibrary.database.annotations.Entidad
public class Entidad {
    @Elemento(columnName = "Id", elementType = TipoElemento.LONG, isPrimary = true)
    private long id;

    @Elemento(columnName = "Nombre")
    private String nombre;

    @Elemento(columnName = "Edad", elementType = TipoElemento.INTEGER)
    private int edad;

    @Elemento(columnName = "Saldo", elementType = TipoElemento.DOUBLE)
    private double saldo;

    @Elemento(columnName = "EsHumano", elementType = TipoElemento.BOOLEAN)
    private boolean esHumano;

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public double getSaldo() {
        return saldo;
    }

    public boolean isEsHumano() {
        return esHumano;
    }
}
