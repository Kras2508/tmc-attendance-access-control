# ADR-06: Use central RBAC and an append-only audit log

* **Status:** Accepted
* **Characteristics:** AC-2, AC-3
* **Report:** Section 4.6

## Context
NFR-3: RBAC plus an audit entry for every administrative operation and every access attempt.

## Decision
The Access Management module handles accounts, roles, authentication and authorization (`AuthorizationApi`). Kiosks authenticate with client certificates. The Audit module stores entries append-only (a database trigger rejects UPDATE/DELETE).

## Consequences
Checks are consistent and in-process. The audit table grows, so a retention policy is needed.

## Where it shows in the code
`central/modules/access-management`, `central/modules/audit`, `V100__audit_init.sql`.
