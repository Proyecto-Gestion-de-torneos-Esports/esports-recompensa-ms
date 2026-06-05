package esports.Recompensas.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExeceptionHandler {

    @ExceptionHandler(RecompensaNotFoundException.class)
    public ResponseEntity<?> manejoRecompensaNoEncontrada(RecompensaNotFoundException e) {
        HashMap<String, Object> error = new HashMap<>();
        error.put("Estado", 404);
        error.put("Mensaje", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<Map<String, String>> handleFeingNotFoundException(FeignException.NotFound ex) {
        Map<String, String> error = new LinkedHashMap<>();
        String urlComprobar = ex.request().url();

        if (urlComprobar.contains("api/torneos")) {
            error.put("error", "Torneo no encontrado ");
            error.put("mensaje", "El torneo no existe o fue eliminado");
        } else if (urlComprobar.contains("/api/equipos")) {
            error.put("error", "Equipo no encontrado ");
            error.put("mensaje", "El eequipo no existe o fue eliminado");

        } else if (urlComprobar.contains("/api/premios")) {
            error.put("error", "Premio no encontrado ");
            error.put("mensaje", "El premio no existe o fue eliminado");
        } else {
            error.put("error", "Recurso no encontrado");
            error.put("mensaje", "El recurso solicitado en el microservicio externo no existe.");

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, String>> handleGeneralFeingException(FeignException ex) {
        Map<String, String> error = new LinkedHashMap<>();

        // microservicio apagado
        String urlComprobar = ex.request() != null ? ex.request().url() : "desconocido";

        if (urlComprobar.contains("api/torneos")) {
            error.put("error", "Error de comunicacion con torneo");
            error.put("mensaje", "El microservicio torneo esta apagado o fallo");

        } else if (urlComprobar.contains("/api/equipos")) {
            error.put("error", "Error de comunicación con Equipos");
            error.put("mensaje", "El microservicio de equipos esta apagado o fallo.");
        } else if (urlComprobar.contains("/api/premios")) {
            error.put("error", "Error de comunicación con premio");
            error.put("mensaje", "El microservicio de premios esta apagado o fallo: ");
        } else {
            error.put("error", "Error de comunicación externa");
            error.put("mensaje", "Ocurrió un error al comunicarse con los microservicios: " + ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> manejoGeneral(Exception e){
        HashMap<String, Object> error = new HashMap<>();
        error.put("Estado", 500);
        error.put("Mensaje", "Error interno en el servidor");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
