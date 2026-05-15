package esports.Recompensas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecompensaResponseDTO {
    private Long recompensa_id;
    private Long torneo_id;
    private Long equipo_id;
    private Long premio_id;
    private Double montoTotal;
    private Integer cantidadIntegrantes;
    private Double montoIndividual;
    private Boolean activo;

}
