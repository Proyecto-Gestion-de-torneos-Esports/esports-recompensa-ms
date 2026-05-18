package esports.Recompensas.controller;

import esports.Recompensas.dto.RecompensaRequestDTO;
import esports.Recompensas.dto.RecompensaResponseDTO;
import esports.Recompensas.service.RecompensaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recompensa")
@RequiredArgsConstructor

public class RecompensaController {

    private final RecompensaService recompensaService;

@GetMapping
public ResponseEntity<List<RecompensaResponseDTO>> obtenerTodos() {
    return ResponseEntity.ok(recompensaService.obtenerTodos());
  }
  @GetMapping("{id}")
    public ResponseEntity<RecompensaResponseDTO> buscarPorId(@PathVariable Long id){
    return ResponseEntity.ok(recompensaService.buscarPorid(id));
  }

  @PostMapping
  public ResponseEntity<RecompensaResponseDTO> procesarRecompensa(@Valid @RequestBody RecompensaRequestDTO dto){
  return ResponseEntity.status(HttpStatus.CREATED).body(recompensaService.ProcesarRecompensa(dto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<RecompensaResponseDTO> actualizarRecompensa(@PathVariable Long id, @Valid @RequestBody RecompensaRequestDTO dto){
  return recompensaService.actualizar(id, dto)
          .map(ResponseEntity::ok)
          .orElseGet(()-> ResponseEntity.notFound().build());

  }
  @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRecompensa(@PathVariable Long id){
    recompensaService.eliminarRecompensa(id);
    return ResponseEntity.noContent().build();
  }

}
