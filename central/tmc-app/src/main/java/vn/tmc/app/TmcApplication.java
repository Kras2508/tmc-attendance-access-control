package vn.tmc.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Bootstrap of the central application quantum ({@code tmc-app}, ADR-01, ADR-08).
 * <p>Composition root only: assembles the seven modules into ONE deployable unit. Contains no business logic.
 */
@SpringBootApplication(scanBasePackages = "vn.tmc")
public class TmcApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmcApplication.class, args);
    }
}
