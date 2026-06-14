package esports.Recompensas.controller;

import esports.Recompensas.assemblers.RecompensaModelAsemblers;
import esports.Recompensas.dto.RecompensaRequestDTO;
import esports.Recompensas.dto.RecompensaResponseDTO;
import esports.Recompensas.service.RecompensaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/recompensa")
@RequiredArgsConstructor
@Tag(name = "Gestión de Recompensas", description = "Endpoints para generar y administrar las recompensas de los equipos")

public class RecompensaController {

  private final RecompensaService recompensaService;
  private final RecompensaModelAsemblers assembler;

  @Operation(summary = "Listar todas las recompensas", description = "Retorna una lista de todas las recompensas activas")
  @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito")
  @GetMapping
  public ResponseEntity<List<RecompensaResponseDTO>> obtenerTodos() {
    List<RecompensaResponseDTO> lista = recompensaService.obtenerTodos().stream()
            .map(assembler::toModel)
            .toList();
    return ResponseEntity.ok(lista);
  }

  @Operation(summary = "Buscar recompensa por ID", description = "Retorna los detalles de una recompensa")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Recompensa encontrada exitosamente"),
          @ApiResponse(responseCode = "404", description = "Recompensa no encontrada o inactiva")
  })
  @GetMapping("/{id}")
  public ResponseEntity<RecompensaResponseDTO> buscarPorId(@PathVariable Long id){
    RecompensaResponseDTO recompensa = recompensaService.buscarPorid(id);
    return ResponseEntity.ok(assembler.toModel(recompensa));
  }

  @Operation(summary = "Procesar nueva recompensa", description = "Genera una nueva recompensa validando los microservicios")
  @ApiResponses({
          @ApiResponse(responseCode = "201", description = "Recompensa procesada y guardada exitosamente"),
          @ApiResponse(responseCode = "400", description = "Datos inválidos (ej. equipo sin integrantes)"),
          @ApiResponse(responseCode = "404", description = "Torneo, equipo o premio no encontrados en los otros microservicios"),
          @ApiResponse(responseCode = "500", description = "Error de comunicación con microservicios externos")
  })
  @PostMapping
  public ResponseEntity<RecompensaResponseDTO> procesarRecompensa(@Valid @RequestBody RecompensaRequestDTO dto){
    RecompensaResponseDTO recompensa = recompensaService.ProcesarRecompensa(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(recompensa));
  }

  @Operation(summary = "Actualizar recompensa", description = "Modifica los datos de una recompensa existente")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Recompensa actualizada correctamente"),
          @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
          @ApiResponse(responseCode = "404", description = "Recompensa no encontrada")
  })
  @PutMapping("/{id}")
  public ResponseEntity<RecompensaResponseDTO> actualizarRecompensa(@PathVariable Long id, @Valid @RequestBody RecompensaRequestDTO dto){
    return recompensaService.actualizar(id, dto)
            .map(recompensa -> ResponseEntity.ok(assembler.toModel(recompensa)))
            .orElseGet(()-> ResponseEntity.notFound().build());
  }

  @Operation(summary = "Eliminar recompensa", description = "Realiza un borrado lógico")
  @ApiResponses({
          @ApiResponse(responseCode = "204", description = "Recompensa eliminada con éxito"),
          @ApiResponse(responseCode = "404", description = "Recompensa no encontrada")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarRecompensa(@PathVariable Long id){
    recompensaService.eliminarRecompensa(id);
    return ResponseEntity.noContent().build();
  }

}
