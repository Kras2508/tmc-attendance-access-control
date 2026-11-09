package vn.tmc.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Bootstrap of the central application quantum ({@code tmc-app}, ADR-01, ADR-08).
 * <p>Composition root only: assembles the seven modules into ONE deployable unit. Contains no business logic.
 */
@SpringBootApplication(scanBasePackages = "vn.tmc")
@EntityScan("vn.tmc")
@EnableJpaRepositories("vn.tmc")
public class TmcApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmcApplication.class, args);
    }
}
