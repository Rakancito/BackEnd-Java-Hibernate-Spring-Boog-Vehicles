package com.test.test.repositoriesJPA;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.test.models.Vehiculo;
import com.test.test.models.Vehiculo_Oficial;
import com.test.test.models.Vehiculo_Residente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.NonNull;

/*
 * Método principal de implementación JPA :: = > Hibernate
 */

@Service
@Transactional
public class VehicleRepositoryService {

    @Autowired
    VehicleRepository vehicleRepositoryService;

    @Autowired
    VehicleOficialRepository vehicleOficialRepositoryService;

    @Autowired
    VehicleResidenteRepository vehicleResidenteRepositoryService;

    @PersistenceContext
    EntityManager objectEntityJPA;

    /*
     * Alta de vehiculo oficial
     */
    public Boolean registerOficialVehicle(String placa)
    {    

        long fecha_registro = System.currentTimeMillis();

        Vehiculo_Oficial vehiculo = new Vehiculo_Oficial();
        vehiculo.SetPlaca(placa);
        vehiculo.setRegistro(fecha_registro);
                
        vehicleOficialRepositoryService.save(vehiculo);

        @SuppressWarnings("null")
        Optional < Vehiculo_Oficial > optional = vehicleOficialRepositoryService.findById(placa);
        if (optional.isPresent())
            return true;
        return false;
    }

    /*
     * Alta de vehiculo residente
    */
    public Boolean registerResidenteVehicle(@NonNull String placa)
    {    
        long fecha_registro = System.currentTimeMillis();

        Vehiculo_Residente vehiculo = new Vehiculo_Residente();
        vehiculo.SetPlaca(placa);
        vehiculo.setRegistro(fecha_registro);
        vehiculo.setTiempo(0);
                
        vehicleResidenteRepositoryService.save(vehiculo);

        Optional < Vehiculo_Residente > optional = vehicleResidenteRepositoryService.findById(placa);
        if (optional.isPresent())
            return true;
        return false;
    }

    public  Optional < Vehiculo_Residente > findResidenteVehicleById(@NonNull String placa)
    {  
        Optional < Vehiculo_Residente > optional = vehicleResidenteRepositoryService.findById(placa);
        return optional;
    }

    public  Optional < Vehiculo_Oficial > findOficialVehicleById(@NonNull String placa)
    {  
        Optional < Vehiculo_Oficial > optional = vehicleOficialRepositoryService.findById(placa);
        return optional;
    }

    /*
     * Registrar entrada
     */
    public Boolean registerEntry(String placa)
    {    
        long fecha_entrada = System.currentTimeMillis();

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.SetPlaca(placa);
        vehiculo.setEntrada(fecha_entrada);
        vehiculo.setSalida(0);
                
        vehicleRepositoryService.save(vehiculo);

        return true;
    }

    public float registerExit(@NonNull String placa)
    {
        List<Vehiculo> vehiculo_lista = (List<Vehiculo>) vehicleRepositoryService.findAll();

        if (vehiculo_lista.isEmpty())
            return -1;

        for (Vehiculo vehiculo_iter : vehiculo_lista) {
            if (vehiculo_iter.getSalida() == 0 && vehiculo_iter.getPlaca().equals(placa))
            {
                Optional < Vehiculo_Residente > optional_residente = vehicleResidenteRepositoryService.findById(placa);
                Optional < Vehiculo_Oficial > optional_oficial = vehicleOficialRepositoryService.findById(placa);

                /* 
                 * Operaciones:
                 *  Guardamos tiempo total que ha usado el estacionamiento.
                 *  Guardamos hora de salida.
                */
                if (optional_residente.isPresent())
                {
                    Vehiculo_Residente vehiculo_residente = objectEntityJPA.find(Vehiculo_Residente.class, placa);
    
                    long tiempo_total = (vehiculo_residente.getTiempo() + (System.currentTimeMillis() - vehiculo_iter.getEntrada()));
                    int tiempo_minutos = (int)tiempo_total/60000;
    
                    //Guardamos datos de salida.                    
                    vehiculo_iter.setSalida(System.currentTimeMillis());
                    vehicleRepositoryService.save(vehiculo_iter);
    
                    //Guardamos tiempo.
                    vehiculo_residente.setTiempo(tiempo_minutos);
                    vehicleResidenteRepositoryService.save(vehiculo_residente);

                    return -2;
                }
                /* 
                 * Operaciones:
                 *  Guardamos hora de salida.
                */
                else if (optional_oficial.isPresent())
                {
                    //Guardamos datos de salida.                    
                    vehiculo_iter.setSalida(System.currentTimeMillis());
                    vehicleRepositoryService.save(vehiculo_iter);
    
                    return 0;
                } 
                /* 
                 * Operaciones:
                 *  Guardamos hora de salida y cobramos.
                */           
                else
                {
                    long tiempo_total = (System.currentTimeMillis() - vehiculo_iter.getEntrada());
                    int tiempo_minutos = (int)tiempo_total/60000;
                    float importe_pagar, razon_cambio = (float)0.5;

                    //Guardamos datos de salida.                    
                    vehiculo_iter.setSalida(System.currentTimeMillis());
                    vehicleRepositoryService.save(vehiculo_iter);

                    if (tiempo_minutos < 1) // Ajuste: Caso demasiado hipotético = menos de un minuto.
                        importe_pagar = razon_cambio;
                    else
                        importe_pagar = tiempo_minutos * razon_cambio;
                        
                    return importe_pagar;
                }
            }
        }

        return -1;
    }

    public void Reset()
    {
        /*
         * Remover tiempo de residentes.
         */
        List<Vehiculo_Residente> vehiculo_lista = (List<Vehiculo_Residente>) vehicleResidenteRepositoryService.findAll();
        if (vehiculo_lista.isEmpty())
            return;

        for (Vehiculo_Residente vehiculo_iter : vehiculo_lista) 
        {
            vehiculo_iter.setTiempo(0);
            vehicleResidenteRepositoryService.save(vehiculo_iter);
        }

        /*
         * Remover a todos los datos de vehiculos, así aseguramos limpieza en la DB.
         */

        vehicleRepositoryService.deleteAll();

        return;
    }

    public List<Vehiculo> listAllVehicles()
    {
        List<Vehiculo> vehiculo_lista = (List<Vehiculo>) vehicleRepositoryService.findAll();
        return vehiculo_lista;
    }

    public List<Vehiculo_Residente> listAllVehiclesResidentes()
    {
        List<Vehiculo_Residente> vehiculo_lista = (List<Vehiculo_Residente>) vehicleResidenteRepositoryService.findAll();
        return vehiculo_lista;
    }

}
