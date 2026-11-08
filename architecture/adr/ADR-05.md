# ADR-05: Use an asynchronous monthly export for the HR/Payroll System

* **Status:** Accepted
* **Characteristics:** AC-4, AC-1, AC-3
* **Report:** Section 4.5

## Context
IC-1: attendance data must be available before 12:00 on day 1 of each month.

## Decision
A pipeline (collect, validate, transform, deliver) starts at 00:30 on day 1, sends one batch with an idempotency key, and retries until the deadline. A database lock ensures that only one instance runs it.

## Consequences
The gate path is never slowed down by the export. Batch status must be stored.

## Where it shows in the code
`reportingexport/presentation/ExportJobTrigger`, `business/export/*`, `PayrollExportPort` ← `HrPayrollAdapter`.
