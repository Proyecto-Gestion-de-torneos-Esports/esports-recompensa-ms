package esports.Recompensas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditoriaRequestDTO {
    private Long idAuditoria;
    private String detalle;
    private LocalDate fecha;

}
