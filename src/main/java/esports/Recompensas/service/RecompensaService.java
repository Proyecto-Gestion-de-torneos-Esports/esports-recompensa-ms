package esports.Recompensas.service;

import esports.Recompensas.client.AuditoriaClient;
import esports.Recompensas.client.PremioClient;
import esports.Recompensas.dto.*;
import esports.Recompensas.exception.RecompensaNotFoundException;
import esports.Recompensas.model.Recompensa;
import esports.Recompensas.repository.RecompensaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecompensaService {
    private final RecompensaRepository recompensaRepository;
    private final PremioClient premioClient;
    private final AuditoriaClient auditoriaClient;

    public RecompensaResponseDTO MapToDto(Recompensa recompensa ) {
        return new RecompensaResponseDTO(
                recompensa.getRecompensa_id(),
                recompensa.getPremioReparto(),
                recompensa.getEquipo_id(),
                recompensa.getPremio_id(),
                recompensa.getTorneo_id(),
                recompensa.getActivo()
                );
    }
    @Transactional(readOnly = true)
    public List<RecompensaResponseDTO> obtenerTodos() {
        log.info("consultado todas las recompensas registradas ");
        return recompensaRepository.findByActivoTrue().stream()
                .map(this::MapToDto)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public RecompensaResponseDTO buscarPorid(Long id){
        log.info("buscando recompensa con ID {} ", id);
        Optional<Recompensa> recompensa = recompensaRepository.findById(id);

        if (recompensa.isPresent() && recompensa.get().getActivo()){
            return recompensa.map(this::MapToDto).orElseThrow();
        }
        log.warn("Recompensa con ID {} no encontrada o inactiva ", id);
        throw new RecompensaNotFoundException("No se puede eliminar, ID " + id + " no encontrado");
    }
@Transactional
public RecompensaResponseDTO generarRecompensa(RecompensaRequestDTO dto){
     log.info("generando para el equipo ID {} en el torneo ID {}", dto.getEquipo_id(), dto.getTorneo_id());

     log.info("consultando microservicio premio para el ID {}", dto.getPremio_id());
    PremioResponseDTO premioInfo = premioClient.obtenerPremioPorId(dto.getPremio_id());

    String calculoReparto;
    if ("Efectivo".equalsIgnoreCase(premioInfo.getTipoPremio())){
        int
    }

    }
    @Transactional
    public void eliminarRecompensa(Long id) {
        log.info(" eliminando recompensa ID {} ", id);
        if (!recompensaRepository.existsById(id)){
            throw new RecompensaNotFoundException("no se pudo eliminar, id " + id + "no encontrado");
        }
        recompensaRepository.findById(id).ifPresent(recompensa -> {
            recompensa.setActivo(false);
            recompensaRepository.save(recompensa);
            generarAuditoria("Se elimino recompensa");

        });

    }
    public void generarAuditoria(String detalle){
        AuditoriaRequestDTO dto = new AuditoriaRequestDTO();
        LocalDate ahora = LocalDate.now();
        dto.setDetalle(detalle);
        dto.setFecha(ahora);

        AuditoriaResponseDTO respuesta = auditoriaClient.generarAuditoria(dto);
    }

}
