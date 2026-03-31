package edu.pe.cibertec.infracciones;

import edu.pe.cibertec.infracciones.model.EstadoMulta;
import edu.pe.cibertec.infracciones.model.Infractor;
import edu.pe.cibertec.infracciones.model.Vehiculo;
import edu.pe.cibertec.infracciones.repository.InfractorRepository;
import edu.pe.cibertec.infracciones.repository.MultaRepository;
import edu.pe.cibertec.infracciones.repository.VehiculoRepository;
import edu.pe.cibertec.infracciones.service.impl.InfractorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Pregunta2Test {

    @Mock
    InfractorRepository infractorRepository;

    @Mock
    VehiculoRepository vehiculoRepository;

    @Mock
    MultaRepository multaRepository;

    @InjectMocks
    InfractorServiceImpl service;

    @Test
    void testDesasignarVehiculo_sinMultasPendientes() {

        Infractor infractor = new Infractor();
        infractor.setVehiculos(new ArrayList<>());

        Vehiculo vehiculo = new Vehiculo();

        // asignar vehículo al infractor
        infractor.getVehiculos().add(vehiculo);

        when(infractorRepository.findById(1L))
                .thenReturn(Optional.of(infractor));

        when(vehiculoRepository.findById(1L))
                .thenReturn(Optional.of(vehiculo));

        // NO hay multas pendientes
        when(multaRepository.existsByVehiculo_IdAndEstado(1L, EstadoMulta.PENDIENTE))
                .thenReturn(false);

        // ejecutar
        service.desasignarVehiculo(1L, 1L);

        // verificar que se removió
        assertFalse(infractor.getVehiculos().contains(vehiculo));
    }
}