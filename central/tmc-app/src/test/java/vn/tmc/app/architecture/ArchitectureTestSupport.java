package vn.tmc.app.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;

import java.util.List;

/**
 * Shared constants for the architecture tests. These tests are the executable form of the
 * dependency rules in report §3.3.1 and of ADR-01 ("module boundaries must be checked in review and tests").
 */
final class ArchitectureTestSupport {

    static final String BASE = "vn.tmc";
    static final String APP = "vn.tmc.app..";
    static final String SHARED = "vn.tmc.shared..";
    static final String QR_TOKEN = "vn.tmc.qrtoken..";
    static final String ADAPTER = "vn.tmc.adapter..";

    /** Domain + cross-cutting modules that have the three-layer structure (Adapter has no layers). */
    static final List<String> LAYERED_MODULES = List.of(
            "identityqr", "gateaccess", "attendance", "reportingexport", "accessmanagement", "audit");

    static final JavaClasses CLASSES = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(BASE);

    static String root(String module) {
        return BASE + "." + module;
    }

    static String all(String module) {
        return root(module) + "..";
    }

    private ArchitectureTestSupport() {
    }
}
