package esports.Recompensas.client;

import esports.Recompensas.dto.PremioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "premio-service", url = "http://localhost:8006/api/premio")
public interface PremioClient {
    @GetMapping("/{id}")
    PremioResponseDTO obtenerPremioPorId(@PathVariable("id") Long id);
}
