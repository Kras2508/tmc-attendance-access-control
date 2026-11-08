# ADR-04: Use a port and an adapter for the turnstile system

* **Status:** Accepted
* **Characteristics:** AC-3, AC-4, AC-1
* **Report:** Section 4.4

## Context
IC-2 requires the existing turnstile API. Its latency is part of the 2-second target.

## Decision
`TurnstilePort` is defined in Gate Access and implemented in the Adapter module. The synchronous call happens after the commit and returns `NOT_OPENED` on timeout.

## Consequences
A change to the API affects one adapter only. The mock and HTTP adapters are interchangeable (LSP).

## Where it shows in the code
`gateaccess/persistence/port/TurnstilePort`, `adapter/turnstile/*`.
