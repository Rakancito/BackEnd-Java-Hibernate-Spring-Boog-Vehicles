package com.test.test.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/*
 * Modelo de vehículos.
 */

@Entity
public class Vehiculo {

    /* START: ATRIBUTOS */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer ID;
    String placa;
    long entrada;
    long salida;

    /* END: ATRIBUTOS */

    /* START :: Getters & Setters */

    /*
     * No es necesario un método para id vehículo.
     */
    public void SetID(int id)
    {
        ID = id;
    }

    public Integer getID()
    {
        return ID;
    }

    public void SetPlaca(String sPlaca_valor)
    {
        placa = sPlaca_valor;
    }

    public String getPlaca()
    {
        return placa;
    }

    public void setEntrada(long entrada_)
    {
        entrada = entrada_;
    }

    public long getEntrada()
    {
        return entrada;
    }

    public void setSalida(long salida_)
    {
        salida = salida_;
    }

    public long getSalida()
    {
        return salida;
    }

    /* END :: Getters & Setters */

    public Vehiculo(String sPlaca_, long lEntrada, long lSalida) 
    {
        super();

        this.placa = sPlaca_;
        this.entrada = lEntrada;
        this.salida = lSalida;
    }

    public Vehiculo() {
        super();
    }

}

