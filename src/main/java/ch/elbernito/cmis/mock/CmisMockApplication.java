
package ch.elbernito.cmis.mock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Haupteinstiegspunkt der CMIS Mock Anwendung.
 */
@SpringBootApplication
public class CmisMockApplication {

    /**
     * Startet die Anwendung.
     *
     * @param args Kommandozeilenargumente
     */
    public static void main(String[] args) {
        SpringApplication.run(CmisMockApplication.class, args);
    }
}
