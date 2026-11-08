/**
 * <b>Gate Access</b> module — domain module of the TMC modular monolith (ADR-01, §3.3.1).
 * <p>Owned requirements: FR-2.1 – FR-2.5, FR-5.3, FR-7.4 – FR-7.6.
 * <p>May depend on: Identity &amp; QR, Attendance, Access Management, Audit, shared kernel.
 * <p>Coordinates the gate-flow UnitOfWork (ADR-02) but never touches Attendance/Audit tables directly.
 * <p>Layering (dependency rule 4): presentation → business → persistence. Other modules see only {@code api}.
 */
package vn.tmc.gateaccess;
