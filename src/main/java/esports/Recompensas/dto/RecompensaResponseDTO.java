package esports.Recompensas.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Estructura de datos que retorna los detalles calculados de la recompensa")
public class RecompensaResponseDTO extends RepresentationModel<RecompensaResponseDTO>{
    @Schema(description = "ID de la recompensa generada", example = "1")
    private Long recompensaId;
    @Schema(description = "ID del torneo", example = "1")
    private Long torneoId;
    @Schema(description = "ID del equipo beneficiado", example = "5")
    private Long equipoId;
    @Schema(description = "ID del premio base", example = "10")
    private Long premioId;
    @Schema(description = "tipo de premio adquirido", example = "EFECTIVO")
    private String tipoPremio;
    @Schema(description = "Valor total del premio", example = "5000.0")
    private Double montoTotal;
    @Schema(description = "Cantidad de jugadores en el equipo", example = "5")
    private Integer cantidadIntegrantes;
    @Schema(description = "Monto que le corresponde a cada jugador ", example = "1000.0")
    private Double montoIndividual;
    @Schema(description = "Estado lógico de la recompensa", example = "true")
    private Boolean activo;

}
