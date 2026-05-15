package esports.Recompensas.service;

import esports.Recompensas.client.AuditoriaClient;
import esports.Recompensas.client.EquipoClient;
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
    private final AuditoriaClient auditoriaClient;
    private final EquipoClient equipoClient;
    private final PremioClient premioClient;


    public RecompensaResponseDTO mapToDto(Recompensa recompensa ) {
        var equipo = equipoClient.obtenerEquipoPorId(recompensa.getEquipoId());
        Integer cantidadIntegrantes = (equipo != null) ? equipo.getCantidadIntegrantes() : 0;
        return new RecompensaResponseDTO(
                recompensa.getRecompensa_id(),
                recompensa.getTorneoId(),
                recompensa.getEquipoId(),
                recompensa.getPremioId(),
                recompensa.getMontoTotal(),
                cantidadIntegrantes,
                recompensa.getMontoIndividual(),
                recompensa.getActivo()
                );
    }
    @Transactional(readOnly = true)
    public List<RecompensaResponseDTO> obtenerTodos() {
        log.info("consultado todas las recompensas registradas ");
        return recompensaRepository.findByActivoTrue().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public RecompensaResponseDTO buscarPorid(Long id){
        log.info("buscando recompensa con ID {} ", id);
        Optional<Recompensa> recompensa = recompensaRepository.findById(id);

        if (recompensa.isPresent() && recompensa.get().getActivo()){
            return recompensa.map(this::mapToDto).orElseThrow();
        }
        log.warn("Recompensa con ID {} no encontrada o inactiva ", id);
        throw new RecompensaNotFoundException("No se puede eliminar, ID " + id + " no encontrado");
    }
@Transactional
public RecompensaResponseDTO ProcesarRecompensa(RecompensaRequestDTO dto){
     log.info("generando para el equipo ID {} en el torneo ID {}", dto.getEquipoId(), dto.getTorneoId());
    //var determina el tipo de dato automaticamente
     var premio = premioClient.obtenerPremioPorId(dto.getPremioId());
     var equipo = equipoClient.obtenerEquipoPorId(dto.getEquipoId());


     Double montoTotal = premio.getCantidadMonto();
     Integer integrantes = equipo.getCantidadIntegrantes();
     Double montoIndividual = 0.0;

     if ("EFECTIVO".equalsIgnoreCase(premio.getTipoPremio())){
         if(integrantes == null || integrantes <= 0) {
             throw new RuntimeException("no hay integrantes para dividir el dinero");
         }
         montoIndividual = montoTotal/integrantes;
     }else {
         log.info("los premios son objetos y no pueden ser divididos");
     }
     Recompensa nueva = new Recompensa();
     nueva.setTorneoId(dto.getTorneoId());
     nueva.setEquipoId(dto.getEquipoId());
     nueva.setPremioId(dto.getPremioId());
     nueva.setMontoTotal(montoTotal);
     nueva.setMontoIndividual(montoIndividual);
     nueva.setActivo(true);

     Recompensa guardada = recompensaRepository.save(nueva);

     return mapToDto(guardada);

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
