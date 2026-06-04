package esports.Recompensas.client;

import esports.Recompensas.dto.TorneoRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//@FeignClient(name = “torneo-service”, url = "http:localhost:8023/api/torneo")
@FeignClient(name = "torneo-service", url = "http://localhost:8003/api/torneos")
public interface TorneoClient {
    @GetMapping("/{id}")
    TorneoRequestDTO obtenerTorneoPorId(@PathVariable Long id);



}
