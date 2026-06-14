package esports.Recompensas;

import esports.Recompensas.model.Recompensa;
import esports.Recompensas.repository.RecompensaRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class DataLoader implements CommandLineRunner {
    private final RecompensaRepository repository;

    public DataLoader(RecompensaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            Faker faker = new Faker(new Locale("es"));
            List<Recompensa> recompensas = new ArrayList<>();

            for (int i = 0; i < 20; i++) {
                Recompensa r = new Recompensa();
                r.setTorneoId(faker.number().numberBetween(1L, 20L));
                r.setEquipoId(faker.number().numberBetween(1L, 50L));
                r.setPremioId(faker.number().numberBetween(1L, 15L));

                double total = Math.round(faker.number().randomDouble(2, 5000, 100000) * 100.0) / 100.0;
                r.setMontoTotal(total);

                double individual = Math.round((total / 5) * 100.0) / 100.0;
                r.setMontoIndividual(individual);

                r.setActivo(true);

                recompensas.add(r);
            }
            repository.saveAll(recompensas);
            System.out.println("20 recompensas generadas exitosamente");
        }
    }
}
