# ADR-03: Use an offline mode at the gate kiosk

* **Status:** Accepted
* **Characteristics:** AC-1, AC-2, AC-5
* **Report:** Section 4.3

## Context
Gates must keep working when the WAN or the central application is unavailable.

## Decision
Local cache and durable outbox on each kiosk. On a 1 s timeout or a 503 response, the kiosk validates locally, stores the event, then opens the gate through the local turnstile system, and uploads the event later using the same eventId.

## Consequences
Gates stay available. A credential revoked after the last cache refresh can still be accepted offline (bounded by the 5-minute refresh and 24-hour expiry). This is a controlled exception to ADR-04.

## Where it shows in the code
`kiosk/kiosk-app` (`ConnectionStateMachine`, `OfflineState`, `Outbox`, `LocalCache`), `KioskArchitectureTest`.
