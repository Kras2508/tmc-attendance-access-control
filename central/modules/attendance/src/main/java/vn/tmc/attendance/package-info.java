/**
 * <b>Attendance</b> module — domain module of the TMC modular monolith (ADR-01, §3.3.1).
 * <p>Owned requirements: FR-3.1 – FR-3.5.
 * <p>May depend on: Access Management, Audit, shared kernel.
 *
 * <p>Layering (dependency rule 4): presentation → business → persistence. Other modules see only {@code api}.
 */
package vn.tmc.attendance;
