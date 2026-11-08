/**
 * <b>Access Management</b> module — cross-cutting module of the TMC modular monolith (ADR-01, §3.3.1).
 * <p>Owned requirements: FR-5.1, FR-5.2, FR-5.5, FR-6.1 – FR-6.6.
 * <p>May depend on: Audit (AuditApi).
 *
 * <p>Layering (dependency rule 4): presentation → business → persistence. Other modules see only {@code api}.
 */
package vn.tmc.accessmanagement;
