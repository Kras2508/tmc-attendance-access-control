package vn.tmc.app.architecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static vn.tmc.app.architecture.ArchitectureTestSupport.ADAPTER;
import static vn.tmc.app.architecture.ArchitectureTestSupport.APP;
import static vn.tmc.app.architecture.ArchitectureTestSupport.CLASSES;
import static vn.tmc.app.architecture.ArchitectureTestSupport.QR_TOKEN;
import static vn.tmc.app.architecture.ArchitectureTestSupport.SHARED;
import static vn.tmc.app.architecture.ArchitectureTestSupport.all;
import static vn.tmc.app.architecture.ArchitectureTestSupport.root;

/**
 * Module-level rules of the modular monolith (report §3.3.1, Figure 1, ADR-01).
 */
class ModuleBoundaryTest {

    static List<String> modules() {
        return ArchitectureTestSupport.LAYERED_MODULES;
    }

    /** Rule 1: a module is called only through its public interface ({@code api}). */
    @ParameterizedTest(name = "internals of {0} are reachable only from {0}")
    @MethodSource("modules")
    void moduleInternalsArePrivate(String module) {
        String r = root(module);
        classes().that().resideInAnyPackage(
                        r + ".presentation..", r + ".business..", r + ".config..",
                        r + ".persistence.repository..", r + ".persistence.entity..")
                .should().onlyHaveDependentClassesThat().resideInAnyPackage(all(module), APP)
                .allowEmptyShould(true)
                .check(CLASSES);
    }

    /** Ports are used by their own module and realized only by the Adapter module. */
    @ParameterizedTest(name = "ports of {0} are used only by {0} and the Adapter module")
    @MethodSource("modules")
    void portsAreOnlyRealizedByAdapters(String module) {
        classes().that().resideInAPackage(root(module) + ".persistence.port..")
                .should().onlyHaveDependentClassesThat().resideInAnyPackage(all(module), ADAPTER, APP)
                .allowEmptyShould(true)
                .check(CLASSES);
    }

    /** Rule 3: dependencies are acyclic. */
    @Test
    void modulesAreFreeOfCycles() {
        slices().matching("vn.tmc.(*)..").should().beFreeOfCycles().check(CLASSES);
    }

    /** Rule 3: no module depends on Reporting &amp; Export, so reporting cannot slow the gate path. */
    @Test
    void noModuleDependsOnReportingExport() {
        noClasses().that().resideOutsideOfPackages(all("reportingexport"), ADAPTER, APP)
                .should().dependOnClassesThat().resideInAPackage(all("reportingexport"))
                .check(CLASSES);
    }

    /** External systems are reached only through ports; business modules never see adapters (ADR-04). */
    @Test
    void noModuleDependsOnAdapters() {
        noClasses().that().resideOutsideOfPackages(ADAPTER, APP)
                .should().dependOnClassesThat().resideInAPackage(ADAPTER)
                .check(CLASSES);
    }

    /** Figure 1: Audit depends on no module. */
    @Test
    void auditDependsOnNoModule() {
        noClasses().that().resideInAPackage(all("audit"))
                .should().dependOnClassesThat().resideInAnyPackage(
                        all("accessmanagement"), all("identityqr"), all("gateaccess"),
                        all("attendance"), all("reportingexport"), ADAPTER, APP)
                .check(CLASSES);
    }

    /** Figure 1: Access Management depends only on Audit. */
    @Test
    void accessManagementDependsOnlyOnAudit() {
        noClasses().that().resideInAPackage(all("accessmanagement"))
                .should().dependOnClassesThat().resideInAnyPackage(
                        all("identityqr"), all("gateaccess"), all("attendance"),
                        all("reportingexport"), ADAPTER, APP)
                .check(CLASSES);
    }

    /** The shared kernel and the token library depend on no module. */
    @Test
    void sharedLibrariesDependOnNoModule() {
        noClasses().that().resideInAnyPackage(SHARED, QR_TOKEN)
                .should().dependOnClassesThat().resideInAnyPackage(
                        all("audit"), all("accessmanagement"), all("identityqr"), all("gateaccess"),
                        all("attendance"), all("reportingexport"), ADAPTER, APP)
                .check(CLASSES);
    }
}
