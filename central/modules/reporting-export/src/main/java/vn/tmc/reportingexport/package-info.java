/**
 * <b>Reporting &amp; Export</b> module — domain module of the TMC modular monolith (ADR-01, §3.3.1).
 * <p>Owned requirements: FR-4.1 – FR-4.3, FR-4.5 – FR-4.7 (IC-1).
 * <p>May depend on: Identity &amp; QR, Attendance, Gate Access (GateStatusApi), Access Management, Audit.
 * <p>Provides NO public interface: no module may depend on it (dependency rule 3), so reporting cannot slow the gate path.
 * <p>Layering (dependency rule 4): presentation → business → persistence. Other modules see only {@code api}.
 */
package vn.tmc.reportingexport;
