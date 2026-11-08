# ADR-07: Protect personal data by role, module, and encryption

* **Status:** Accepted
* **Characteristics:** AC-2
* **Report:** Section 4.7

## Context
NFR-4: personal data must be protected.

## Decision
Role- and scope-based access. Reports contain only the personal data they need. Each module owns its own schema. Data is encrypted at rest and in transit, and network zones separate the tiers.

## Consequences
Retention periods must be confirmed with TMC.

## Where it shows in the code
One schema per module, `ConventionTest.publicInterfacesDoNotExposeEntities`, `ReportService` scope filtering.
