package esports.Recompensas.controller;

import esports.Recompensas.dto.RecompensaResponseDTO;
import esports.Recompensas.service.RecompensaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
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

  @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRecompensa(@PathVariable Long id){
    recompensaService.eliminarRecompensa(id);
    return ResponseEntity.noContent().build();
  }

}
