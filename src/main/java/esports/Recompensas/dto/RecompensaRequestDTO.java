package esports.Recompensas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Estructura de datos para solicitar la generación de una recompensa")
public class RecompensaRequestDTO {


    @Schema(description = "ID del torneo donde participó el equipo", example = "1")
    @NotNull(message = "El ID torneo es obligatorio")
    private Long torneoId;

    @Schema(description = "ID del equipo ganador", example = "5")
    @NotNull(message = "El ID del equipo es obligatorio")
    private Long equipoId;

    @Schema(description = "ID del premio asignado desde el inventario", example = "10")
    @NotNull(message = "el ID del premio es obligatorio")
    private Long premioId;

    @Schema(description = "Estado de la recompensa", example = "true")
    @NotNull(message = "El campo activo es obligatorio")
    private Boolean activo = true;
}
