package com.test.test.repositoriesJPA;

import org.springframework.data.repository.CrudRepository;

import com.test.test.models.Vehiculo_Residente;

/*
 * Interfaz de JPA
 */

 public interface VehicleResidenteRepository extends CrudRepository<Vehiculo_Residente, String> 
 {
 
 }
