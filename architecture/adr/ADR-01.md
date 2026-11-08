# ADR-01: Use a modular monolith for the central application

* **Status:** Accepted
* **Characteristics:** AC-1, AC-2, AC-3, AC-4, AC-5
* **Report:** Section 4.1

## Context
All backend capabilities share the same driving characteristics and the load is low (~0.44 tx/s per factory). The central application is one quantum; the gate kiosk is a separate, smaller quantum.

## Decision
One deployable unit (`tmc-app`) split into seven modules by business domain. Each module has a public interface and three layers (presentation, business, persistence).

## Consequences
One process and one database give atomic commits, consistent security and audit, and in-process calls on the gate path. The backend is deployed and scaled as one unit, so module boundaries must be enforced.

## Where it shows in the code
`central/modules/*` (one Maven module per architecture module), `central/tmc-app`, `ModuleBoundaryTest`, `LayerDependencyTest`.
