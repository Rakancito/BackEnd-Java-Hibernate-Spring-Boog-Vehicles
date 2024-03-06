package com.test.test.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.test.test.models.Vehiculo_Residente;
import com.test.test.repositoriesJPA.VehicleRepositoryService;
import com.test.test.utils.generarPagosPDF;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class VehicleController {

    @Autowired
    VehicleRepositoryService vehicleRepositoryManagerJPA;

    @GetMapping("/altaOficialVehicle/{placa}")
    public String registrarAltaOficial(@PathVariable String placa) {
        if (vehicleRepositoryManagerJPA.registerOficialVehicle(placa))
            return "Vehiculo oficial registrado con placa: " + placa;

        return "Ha ocurrido un error registrando el vehiculo.";
    }

    @GetMapping("/altaResidenteVehicle/{placa}")
    public String registerResidenteVehicle(@PathVariable String placa) {
        if (vehicleRepositoryManagerJPA.registerResidenteVehicle(placa))
            return "Vehiculo residente registrado con placa: " + placa;

        return "Ha ocurrido un error registrando el vehiculo.";
    }

    @GetMapping("/registrarEntrada/{placa}")
    public String registrarEntrada(@PathVariable String placa) {
        if (vehicleRepositoryManagerJPA.registerEntry(placa))
            return "Entrada registrada placa: " + placa;
        else
            return "Entrada no registrada, no se pudo realizar la operación.";
    }

    @GetMapping("/registrarSalida/{placa}")
    public String registrarSalida(@PathVariable String placa) {
        float importe = vehicleRepositoryManagerJPA.registerExit(placa);
        if (importe == 0)
            return "Entrada registrada placa: " + placa + " como es vehiculo oficial no deberá pagar.";
        else if (importe == -2)
            return "Entrada registrada placa: " + placa + " como es vehiculo residente deberá pagar al final del mes.";
        else if (importe > 0)
            return "Entrada registrada placa: " + placa + " el importe total a pagar es de: " + importe;
        else
            return "Entrada no registrada, no se pudo realizar la operación error: " + importe;
    }

    @GetMapping("/comienzaMes")
    public String comenzarMes() {
        vehicleRepositoryManagerJPA.Reset();
        return "El mes se ha reiniciado...";
    }

    @GetMapping("/pagoResidente/{nombre_archivo}")
    public void generarPago(HttpServletResponse response, @PathVariable String nombre_archivo)  throws DocumentException, IOException
    {
		response.setContentType("application/pdf");
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename="+nombre_archivo+".pdf";
		
		response.setHeader(cabecera, valor);
		
		List<Vehiculo_Residente> vehiculos_residentes = vehicleRepositoryManagerJPA.listAllVehiclesResidentes();
		
		generarPagosPDF exporter = new generarPagosPDF(vehiculos_residentes);
		exporter.exportar(response);
        return;
    }
    
}
