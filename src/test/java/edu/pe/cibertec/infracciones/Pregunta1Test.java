package edu.pe.cibertec.infracciones;

import edu.pe.cibertec.infracciones.model.EstadoMulta;
import edu.pe.cibertec.infracciones.model.Infractor;
import edu.pe.cibertec.infracciones.model.Multa;
import edu.pe.cibertec.infracciones.repository.InfractorRepository;
import edu.pe.cibertec.infracciones.repository.MultaRepository;
import edu.pe.cibertec.infracciones.service.impl.InfractorServiceImpl;
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
class Pregunta1Test {

    @Mock
    InfractorRepository infractorRepository;

    @Mock
    MultaRepository multaRepository;

    @InjectMocks
    InfractorServiceImpl service;

    @Test
    void testCalcularDeuda() {

        Multa m1 = new Multa();
        m1.setMonto(200.0);

        Multa m2 = new Multa();
        m2.setMonto(300.0);

        when(multaRepository.findByInfractor_IdAndEstado(1L, EstadoMulta.PENDIENTE))
                .thenReturn(List.of(m1));

        when(multaRepository.findByInfractor_IdAndEstado(1L, EstadoMulta.VENCIDA))
                .thenReturn(List.of(m2));

        double resultado = service.calcularDeuda(1L);

        assertEquals(545.0, resultado, 0.01);
    }
}