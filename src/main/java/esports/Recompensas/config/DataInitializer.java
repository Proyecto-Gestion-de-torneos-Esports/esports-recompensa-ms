package esports.Recompensas.config;

import esports.Recompensas.model.Recompensa;
import esports.Recompensas.repository.RecompensaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RecompensaRepository recompensaRepository;

    @Override
    public void run(String... args) throws Exception {
        if (recompensaRepository.count() == 0) {



            Recompensa rec1 = new Recompensa(null, "$2000 por jugador", 1L, 10L, 100L, true);


            Recompensa rec2 = new Recompensa(null, "1 Teclado Mecánico por jugador", 2L, 20L, 200L, true);

            recompensaRepository.saveAll(List.of(rec1, rec2));


        } else {
            log.info(">>> DataInitializer: La base de datos ya contiene recompensas.");
        }
    }
}
