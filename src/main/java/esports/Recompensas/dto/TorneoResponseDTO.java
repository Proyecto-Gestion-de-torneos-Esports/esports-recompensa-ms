package esports.Recompensas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TorneoResponseDTO {
    private Long torneoId;
    private String nombre;
    private LocalDate fecha;
    private String lugar;
    private Long idJuego;
    private String estado;
    private Integer cantidadPartidas;
    private List<Object> partidas;
    private Set<Long> equiposInscritos;

}
