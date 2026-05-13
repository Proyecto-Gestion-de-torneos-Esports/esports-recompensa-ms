package esports.Recompensas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RecompensaRequestDTO {
    @NotNull(message = "El ID del equipo es obligatorio")
    private Long equipo_id;

    @NotNull(message = "el ID del premio es obligatorio")
    private Long premio_id;

    @NotNull(message = "El ID torneo es obligatorio")
    private Long torneo_id;

    private Boolean activo = true;
}
