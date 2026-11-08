/**
 * BUSINESS layer of the Identity & QR module: the facade (single entry point) and the services.
 * <p>Rules: depends on {@code persistence} (repositories, ports) and on other modules' {@code api} only;
 * never on {@code presentation}. Dependencies are constructor-injected interfaces (DIP).
 */
package vn.tmc.identityqr.business;
