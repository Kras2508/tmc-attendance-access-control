/**
 * <b>Audit</b> module — cross-cutting module of the TMC modular monolith (ADR-01, §3.3.1).
 * <p>Owned requirements: FR-4.4 (and NFR-3).
 * <p>May depend on: shared kernel only (Audit depends on no module).
 * <p>No presentation layer: audit inspection is offered to users by Reporting &amp; Export (Table 12).
 * <p>Layering (dependency rule 4): presentation → business → persistence. Other modules see only {@code api}.
 */
package vn.tmc.audit;
