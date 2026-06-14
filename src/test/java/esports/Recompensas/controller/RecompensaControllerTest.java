package esports.Recompensas.controller;
import esports.Recompensas.dto.RecompensaRequestDTO;
import esports.Recompensas.dto.RecompensaResponseDTO;
import esports.Recompensas.service.RecompensaService;
import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import org.springframework.http.MediaType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest(RecompensaController.class)
public class RecompensaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RecompensaService recompensaService;

    @Autowired
    private ObjectMapper objectMapper;

    private RecompensaRequestDTO requestDTO;
    private RecompensaResponseDTO responseDTO;

    @BeforeEach
    void setUp() {

        requestDTO = new RecompensaRequestDTO();
        requestDTO.setTorneoId(1L);
        requestDTO.setEquipoId(1L);
        requestDTO.setPremioId(1L);
        requestDTO.setActivo(true);

        responseDTO = new RecompensaResponseDTO(
                1L, 1L, 1L, 1L, "EFECTIVO", 1000.0, 5, 200.0, true
        );
    }

    @Test
    void obtenerTodosConExito() throws Exception {
        when(recompensaService.obtenerTodos()).thenReturn(Arrays.asList(responseDTO));

        mockMvc.perform(get("/api/recompensa")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].recompensaId").value(1L))
                .andExpect(jsonPath("$[0].tipoPremio").value("EFECTIVO"))
                .andExpect(jsonPath("$[0].montoIndividual").value(200.0));
    }

    @Test
    void buscarPorIdConExito() throws Exception {
        when(recompensaService.buscarPorid(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/recompensa/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recompensaId").value(1L))
                .andExpect(jsonPath("$.montoTotal").value(1000.0));
    }

    @Test
    void procesarRecompensaConExito() throws Exception {
        when(recompensaService.ProcesarRecompensa(any(RecompensaRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/recompensa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.recompensaId").value(1L))
                .andExpect(jsonPath("$.tipoPremio").value("EFECTIVO"));
    }

    @Test
    void actualizarConExito() throws Exception {
        when(recompensaService.actualizar(anyLong(), any(RecompensaRequestDTO.class))).thenReturn(Optional.of(responseDTO));

        mockMvc.perform(put("/api/recompensa/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recompensaId").value(1L));
    }

    @Test
    void eliminarConExito() throws Exception {
        doNothing().when(recompensaService).eliminarRecompensa(1L);

        mockMvc.perform(delete("/api/recompensa/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}

