package esports.Recompensas.client;

import esports.Recompensas.dto.EquipoResponseDTO;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "equipo-service", url = "http://localhost:8002/api/equipos")
public interface EquipoClient {
    @GetMapping("/{id}")
    EquipoResponseDTO obtenerEquipoPorId(@PathVariable("id") Long id);


    //DTO espejo captura datos especificos
    @Data
    class EquipoExternalDTO{
        private Long equipo;
        private Integer cantidadIntegrantes;
    }
}

