package esports.Recompensas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    private Long recompensa_id;

    @Column(name = "premio_reparto", length = 50)
    private String premioReparto;

    private Long equipo_Id;

    private Long premio_id;

    private boolean activo;


}
