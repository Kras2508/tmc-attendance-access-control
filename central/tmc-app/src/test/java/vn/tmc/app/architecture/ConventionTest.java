package vn.tmc.app.architecture;

import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static vn.tmc.app.architecture.ArchitectureTestSupport.CLASSES;

/**
 * Structural conventions that make the SOLID application of §5.1 visible in the code.
 */
class ConventionTest {

    /** ISP/DIP: public interfaces (Table 6) are Java interfaces; DTOs live in api.dto. */
    @Test
    void publicInterfacesAreInterfaces() {
        classes().that().resideInAPackage("vn.tmc.*.api")
                .and().doNotHaveSimpleName("package-info")
                .should().beInterfaces()
                .allowEmptyShould(true)
                .check(CLASSES);
    }

    /** DIP/OCP: ports (Table 6) are Java interfaces implemented in the Adapter module. */
    @Test
    void portsAreInterfaces() {
        classes().that().resideInAPackage("vn.tmc.*.persistence.port..")
                .and().doNotHaveSimpleName("package-info")
                .should().beInterfaces()
                .allowEmptyShould(true)
                .check(CLASSES);
    }

    /** ADR-07: persistence entities never leak through a public interface. */
    @Test
    void publicInterfacesDoNotExposeEntities() {
        noClasses().that().resideInAPackage("vn.tmc.*.api..")
                .should().dependOnClassesThat().resideInAPackage("vn.tmc.*.persistence..")
                .allowEmptyShould(true)
                .check(CLASSES);
    }

    /** Adapters touch only ports, never business, repositories or entities of a module. */
    @Test
    void adaptersDependOnlyOnPorts() {
        noClasses().that().resideInAPackage("vn.tmc.adapter..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "vn.tmc.*.presentation..", "vn.tmc.*.business..",
                        "vn.tmc.*.persistence.repository..", "vn.tmc.*.persistence.entity..")
                .check(CLASSES);
    }
}
