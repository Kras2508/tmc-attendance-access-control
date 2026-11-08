# ADR-02: Store the attendance record and audit entry in one transaction

* **Status:** Accepted
* **Characteristics:** AC-1, AC-2, AC-3
* **Report:** Section 4.2

## Context
NFR-2: no attendance record may be lost. NFR-3: every access attempt is audited.

## Decision
One database transaction commits the attendance record, its audit entry and the nonce before the gate is opened. If the turnstile call fails afterwards, the event is marked abnormal and never deleted.

## Consequences
Avoids a distributed transaction. The commit plus synchronous replication costs about 150 ms of the 2-second budget.

## Where it shows in the code
`shared-kernel/.../tx/UnitOfWork`, `tmc-app/.../TransactionTemplateUnitOfWork`, `gateaccess/.../AccessDecisionService`.
