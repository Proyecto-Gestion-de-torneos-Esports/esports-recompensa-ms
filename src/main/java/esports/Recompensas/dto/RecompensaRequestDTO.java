package esports.Recompensas.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RecompensaRequestDTO {



    @NotNull(message = "El ID torneo es obligatorio")
    private Long torneoId;

    @NotNull(message = "El ID del equipo es obligatorio")
    private Long equipoId;

    @NotNull(message = "el ID del premio es obligatorio")
    private Long premioId;
    @NotNull(message = "El campo activo es obligatorio")
    private Boolean activo = true;
}
