package esports.Recompensas.service;
import esports.Recompensas.dto.RecompensaResponseDTO;
import esports.Recompensas.exception.RecompensaNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;


import esports.Recompensas.client.AuditoriaClient;
import esports.Recompensas.client.EquipoClient;
import esports.Recompensas.client.PremioClient;
import esports.Recompensas.client.TorneoClient;
import esports.Recompensas.dto.EquipoResponseDTO;
import esports.Recompensas.dto.RecompensaRequestDTO;
import esports.Recompensas.dto.TorneoRequestDTO;
import esports.Recompensas.model.Recompensa;
import esports.Recompensas.repository.RecompensaRepository;

@ExtendWith(MockitoExtension.class)
public class RecompensaServiceTest {
    @Mock
    private RecompensaRepository recompensaRepository;

    @Mock
    private AuditoriaClient auditoriaClient;

    @Mock
    private EquipoClient equipoClient;

    @Mock
    private PremioClient premioClient;

    @Mock
    private TorneoClient torneoClient;

    @InjectMocks
    private RecompensaService recompensaService;

    private Recompensa recompensaBase;
    private RecompensaRequestDTO requestDTO;


    private PremioClient.PremioExternalDTO premioExternalDTO;
    private EquipoResponseDTO equipoResponseDTO;
    private TorneoRequestDTO torneoRequestDTO;

    @BeforeEach
    void setUp() {

        recompensaBase = new Recompensa(1L, 1L, 1L, 1L, 1000.0, 200.0, true);

        requestDTO = new RecompensaRequestDTO();
        requestDTO.setTorneoId(1L);
        requestDTO.setEquipoId(1L);
        requestDTO.setPremioId(1L);
        requestDTO.setActivo(true);

        torneoRequestDTO = new TorneoRequestDTO();

        premioExternalDTO = new PremioClient.PremioExternalDTO();
        premioExternalDTO.setPremioId(1L);
        premioExternalDTO.setTipoPremio("EFECTIVO");
        premioExternalDTO.setCantidadMonto(1000.0);

        equipoResponseDTO = new EquipoResponseDTO();
        equipoResponseDTO.setCantidadIntegrantes(5);
    }

    @Test
    void obtenerTodosConExito() {
        when(recompensaRepository.findByActivoTrue()).thenReturn(Arrays.asList(recompensaBase));

        when(equipoClient.obtenerEquipoPorId(1L)).thenReturn(equipoResponseDTO);
        when(premioClient.obtenerPremioPorId(1L)).thenReturn(premioExternalDTO);

        List<RecompensaResponseDTO> resultado = recompensaService.obtenerTodos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(200.0, resultado.get(0).getMontoIndividual());
        verify(recompensaRepository, times(1)).findByActivoTrue();
    }

    @Test
    void buscarPorIdConExito() {
        when(recompensaRepository.findById(1L)).thenReturn(Optional.of(recompensaBase));
        when(equipoClient.obtenerEquipoPorId(1L)).thenReturn(equipoResponseDTO);
        when(premioClient.obtenerPremioPorId(1L)).thenReturn(premioExternalDTO);

        RecompensaResponseDTO resultado = recompensaService.buscarPorid(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getRecompensaId());
        assertEquals("EFECTIVO", resultado.getTipoPremio());
    }

    @Test
    void buscarPorIdNoExiste() {
        when(recompensaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecompensaNotFoundException.class, () -> recompensaService.buscarPorid(99L));
    }

    @Test
    void procesarRecompensaConExito() {

        when(torneoClient.obtenerTorneoPorId(1L)).thenReturn(torneoRequestDTO);
        when(premioClient.obtenerPremioPorId(1L)).thenReturn(premioExternalDTO);
        when(equipoClient.obtenerEquipoPorId(1L)).thenReturn(equipoResponseDTO);


        when(recompensaRepository.save(any(Recompensa.class))).thenAnswer(invocation -> {
            Recompensa r = invocation.getArgument(0);
            r.setRecompensaId(1L);
            return r;
        });

        RecompensaResponseDTO resultado = recompensaService.ProcesarRecompensa(requestDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getRecompensaId());
        assertEquals(1000.0, resultado.getMontoTotal());


        assertEquals(200.0, resultado.getMontoIndividual());

        verify(recompensaRepository, times(1)).save(any(Recompensa.class));
        verify(auditoriaClient, times(1)).generarAuditoria(any());
    }

    @Test
    void eliminarConExito() {
        when(recompensaRepository.existsById(1L)).thenReturn(true);
        when(recompensaRepository.findById(1L)).thenReturn(Optional.of(recompensaBase));

        recompensaService.eliminarRecompensa(1L);

        assertFalse(recompensaBase.getActivo());
        verify(recompensaRepository, times(1)).save(recompensaBase);
        verify(auditoriaClient, times(1)).generarAuditoria(any());
    }
}

