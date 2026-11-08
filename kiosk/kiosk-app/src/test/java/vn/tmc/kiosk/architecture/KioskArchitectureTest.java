package vn.tmc.kiosk.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Rules of the kiosk quantum (§3.1.2, ADR-03, Figure 22).
 */
class KioskArchitectureTest {

    private static final JavaClasses CLASSES = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("vn.tmc");

    /** Separate quantum: the kiosk talks to the central application only over ScanApi (HTTPS). */
    @Test
    void kioskDoesNotDependOnCentralModules() {
        noClasses().that().resideInAPackage("vn.tmc.kiosk..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "vn.tmc.app..", "vn.tmc.audit..", "vn.tmc.accessmanagement..", "vn.tmc.identityqr..",
                        "vn.tmc.gateaccess..", "vn.tmc.attendance..", "vn.tmc.reportingexport..", "vn.tmc.adapter..")
                .check(CLASSES);
    }

    /** DIP: application logic depends on ports, never on infrastructure implementations. */
    @Test
    void applicationDoesNotDependOnInfrastructure() {
        noClasses().that().resideInAnyPackage("vn.tmc.kiosk.application..", "vn.tmc.kiosk.domain..",
                        "vn.tmc.kiosk.port..", "vn.tmc.kiosk.presentation..")
                .should().dependOnClassesThat().resideInAPackage("vn.tmc.kiosk.infrastructure..")
                .check(CLASSES);
    }

    @Test
    void portsAreInterfaces() {
        classes().that().resideInAPackage("vn.tmc.kiosk.port..")
                .and().doNotHaveSimpleName("package-info")
                .should().beInterfaces()
                .check(CLASSES);
    }
}
