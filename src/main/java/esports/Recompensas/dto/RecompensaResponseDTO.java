package esports.Recompensas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecompensaResponseDTO {
    private Long recompensaId;
    private Long torneoId;
    private Long equipoId;
    private Long premioId;
    private String tipoPremio;
    private Double montoTotal;
    private Integer cantidadIntegrantes;
    private Double montoIndividual;
    private Boolean activo;

}
