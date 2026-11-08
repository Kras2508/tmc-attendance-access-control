package vn.tmc.kiosk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Bootstrap of the gate kiosk — the second, smaller architecture quantum (§3.1.2, ADR-03).
 * <p>Deployed on every gate lane, independently of {@code tmc-app}. Contains no business logic.
 */
@SpringBootApplication
public class KioskApplication {

    public static void main(String[] args) {
        SpringApplication.run(KioskApplication.class, args);
    }
}
