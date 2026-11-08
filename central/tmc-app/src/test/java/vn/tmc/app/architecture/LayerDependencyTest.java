package vn.tmc.app.architecture;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static com.tngtech.archunit.base.DescribedPredicate.not;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.simpleNameEndingWith;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static vn.tmc.app.architecture.ArchitectureTestSupport.CLASSES;
import static vn.tmc.app.architecture.ArchitectureTestSupport.root;

/**
 * Rule 4 of §3.3.1: inside a module each layer calls only the layer below it
 * (presentation → business → persistence), and the facade is the single entry point.
 */
class LayerDependencyTest {

    static List<String> modules() {
        return ArchitectureTestSupport.LAYERED_MODULES;
    }

    @ParameterizedTest(name = "{0}: business does not depend on presentation")
    @MethodSource("modules")
    void businessDoesNotDependOnPresentation(String module) {
        noClasses().that().resideInAPackage(root(module) + ".business..")
                .should().dependOnClassesThat().resideInAPackage(root(module) + ".presentation..")
                .allowEmptyShould(true)
                .check(CLASSES);
    }

    @ParameterizedTest(name = "{0}: persistence does not depend on business or presentation")
    @MethodSource("modules")
    void persistenceIsTheLowestLayer(String module) {
        noClasses().that().resideInAPackage(root(module) + ".persistence..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        root(module) + ".business..", root(module) + ".presentation..")
                .allowEmptyShould(true)
                .check(CLASSES);
    }

    @ParameterizedTest(name = "{0}: presentation does not skip the business layer")
    @MethodSource("modules")
    void presentationDoesNotAccessPersistence(String module) {
        noClasses().that().resideInAPackage(root(module) + ".presentation..")
                .should().dependOnClassesThat().resideInAPackage(root(module) + ".persistence..")
                .allowEmptyShould(true)
                .check(CLASSES);
    }

    @ParameterizedTest(name = "{0}: presentation talks to the business layer only through the facade")
    @MethodSource("modules")
    void presentationUsesOnlyTheFacade(String module) {
        noClasses().that().resideInAPackage(root(module) + ".presentation..")
                .should().dependOnClassesThat(
                        resideInAPackage(root(module) + ".business..").and(not(simpleNameEndingWith("Facade"))))
                .allowEmptyShould(true)
                .check(CLASSES);
    }
}
