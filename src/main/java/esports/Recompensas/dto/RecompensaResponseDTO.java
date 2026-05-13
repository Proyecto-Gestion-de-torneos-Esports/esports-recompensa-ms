package esports.Recompensas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecompensaResponseDTO {
    private Long id;
    private String premioReparto;
    private Long equipo_id;
    private Long premio_id;
    private Long torneo_id;
    private Boolean activo;

}
