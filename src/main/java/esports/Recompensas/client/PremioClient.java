package esports.Recompensas.client;

import esports.Recompensas.dto.PremioResponseDTO;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "premios", path = "/api/premios")
public interface PremioClient {
    @GetMapping("/{premioId}")
    PremioExternalDTO obtenerPremioPorId(@PathVariable Long premioId);

    //DTO espejo
    @Data
    class PremioExternalDTO {
        private Long premioId;
        private Double cantidadMonto;
        private String tipoPremio; // EFECTIVO o objeto
    }
}