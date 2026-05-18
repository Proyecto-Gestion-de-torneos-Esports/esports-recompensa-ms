package esports.Recompensas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recompensa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recompensa_id")
    private Long recompensaId;

    @Column(name = "torneo_id", nullable = false)
    private Long torneoId;

    @Column(name = "equipo_id", nullable = false)
    private Long equipoId;

    @Column(name = "premio_id", nullable = false)
    private Long premioId;


    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;      // Lo que costaba el premio entero

    @Column(name = "monto_individual", nullable = false)
    private Double montoIndividual;

    @Column(nullable = false)
    private Boolean activo = true;

}
