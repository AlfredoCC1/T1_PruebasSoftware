package edu.pe.cibertec.infracciones;

import edu.pe.cibertec.infracciones.model.*;
import edu.pe.cibertec.infracciones.repository.InfractorRepository;
import edu.pe.cibertec.infracciones.repository.MultaRepository;
import edu.pe.cibertec.infracciones.service.impl.MultaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Pregunta3Test {

    @Mock
    MultaRepository multaRepository;

    @Mock
    InfractorRepository infractorRepository;

    @InjectMocks
    MultaServiceImpl service;

    @Test
    void testTransferirMulta() {

        Multa multa = new Multa();
        multa.setEstado(EstadoMulta.PENDIENTE);

        Vehiculo vehiculo = new Vehiculo();
        multa.setVehiculo(vehiculo);

        Infractor nuevo = new Infractor();
        nuevo.setBloqueado(false);
        nuevo.setVehiculos(List.of(vehiculo));

        when(multaRepository.findById(1L))
                .thenReturn(Optional.of(multa));

        when(infractorRepository.findById(2L))
                .thenReturn(Optional.of(nuevo));

        service.transferirMulta(1L, 2L);

        assertEquals(nuevo, multa.getInfractor());
    }
}