package esports.Recompensas.service;

import esports.Recompensas.dto.RecompensaRequestDTO;
import esports.Recompensas.dto.RecompensaResponseDTO;
import esports.Recompensas.exception.RecompensaNotFoundException;
import esports.Recompensas.model.Recompensa;
import esports.Recompensas.repository.RecompensaRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecompensaService {
    private final RecompensaRepository recompensaRepository;

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
    public void eliminarRecompensa(Long id) {
        log.info(" eliminando recompensa ID {} ", id);
        if (!recompensaRepository.existsById(id)){
            throw new RecompensaNotFoundException("no se pudo eliminar, id " + id + "no encontrado");
        }
        recompensaRepository.findById(id).ifPresent(recompensa -> {
            recompensa.setActivo(false);
            recompensaRepository.save(recompensa);

        });

    }

}
