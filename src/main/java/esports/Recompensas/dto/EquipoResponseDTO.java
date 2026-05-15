package esports.Recompensas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EquipoResponseDTO {
    private Long equipoId;
    private String nombre;
    private Integer cantidadIntegrantes;
}
