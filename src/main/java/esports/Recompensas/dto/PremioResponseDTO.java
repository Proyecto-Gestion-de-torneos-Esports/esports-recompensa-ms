package esports.Recompensas.dto;

import lombok.Data;

@Data
public class PremioResponseDTO {
    private Long id;
    private String tipoPremio;
    private String descripcion;
    private Integer cantidadMonto;
    private Boolean activo;
}
