package com.test.test.repositoriesJPA;

import org.springframework.data.repository.CrudRepository;

import com.test.test.models.Vehiculo;



/*
 * Interfaz de JPA
*/

public interface VehicleRepository extends CrudRepository<Vehiculo, Integer> 
{

}

