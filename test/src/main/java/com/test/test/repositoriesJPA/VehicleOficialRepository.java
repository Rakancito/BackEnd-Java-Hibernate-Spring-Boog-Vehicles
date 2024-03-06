package com.test.test.repositoriesJPA;

import org.springframework.data.repository.CrudRepository;

import com.test.test.models.Vehiculo_Oficial;


/*
 * Interfaz de JPA
 */

 public interface VehicleOficialRepository extends CrudRepository<Vehiculo_Oficial, String> 
 {

 }
