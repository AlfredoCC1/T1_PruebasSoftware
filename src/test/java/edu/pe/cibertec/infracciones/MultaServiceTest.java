package edu.pe.cibertec.infracciones;

import edu.pe.cibertec.infracciones.exception.InfractorBloqueadoException;
import edu.pe.cibertec.infracciones.model.EstadoMulta;
import edu.pe.cibertec.infracciones.model.Infractor;
import edu.pe.cibertec.infracciones.model.Multa;
import edu.pe.cibertec.infracciones.model.Vehiculo;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MultaServiceTest {

    @Mock
    MultaRepository multaRepository;

    @Mock
    InfractorRepository infractorRepository;

    @InjectMocks
    MultaServiceImpl multaService;

    @Test
    void transferirMultaInfractorBloqueado() {

        Vehiculo vehiculo = new Vehiculo();

        Multa multa = new Multa();
        multa.setId(1L);
        multa.setEstado(EstadoMulta.PENDIENTE);
        multa.setVehiculo(vehiculo);

        Infractor infractorBloqueado = new Infractor();
        infractorBloqueado.setId(2L);
        infractorBloqueado.setBloqueado(true);
        infractorBloqueado.setVehiculos(List.of(vehiculo));

        when(multaRepository.findById(1L)).thenReturn(Optional.of(multa));
        when(infractorRepository.findById(2L)).thenReturn(Optional.of(infractorBloqueado));

        assertThrows(InfractorBloqueadoException.class, () ->
                multaService.transferirMulta(1L, 2L)
        );

        verify(multaRepository, never()).save(any(Multa.class));
    }
}