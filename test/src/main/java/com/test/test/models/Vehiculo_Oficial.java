package com.test.test.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/*
 * Modelo de vehículos.
 */

@Entity
public class Vehiculo_Oficial {
    /* START: ATRIBUTOS */
    @Id
    String placa;
    long fecha_registro;

    /* END: ATRIBUTOS */

    /* START :: Getters & Setters */

    /*
     * No es necesario un método para id vehículo.
     */

    public void SetPlaca(String sPlaca_valor)
    {
        placa = sPlaca_valor;
    }

    public String getPlaca()
    {
        return placa;
    }

    public void setRegistro(long registro)
    {
        fecha_registro = registro;
    }

    public long getRegistro()
    {
        return fecha_registro;
    }

    /* END :: Getters & Setters */

    public Vehiculo_Oficial(String sPlaca_, long lfecha_registro) 
    {
        super();

        this.placa = sPlaca_;
        this.fecha_registro = lfecha_registro;
    }

    public Vehiculo_Oficial() {
        super();
    }
}
