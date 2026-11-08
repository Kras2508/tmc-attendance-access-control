# ADR-08: Use two application servers and a standby database

* **Status:** Accepted
* **Characteristics:** AC-1, AC-3
* **Report:** Section 4.8

## Context
Gates must be available at every shift change. Redundancy is needed for availability, not for capacity.

## Decision
One Docker image `tmc-app` runs on two stateless servers behind two load balancers, with a primary database plus a synchronous standby. If the standby is unavailable, the application returns 503 and kiosks switch to offline mode. No Kubernetes.

## Consequences
Low cost and enough availability. If the whole server room fails, central functions stop but gates keep working offline.

## Where it shows in the code
`docker/tmc-app.Dockerfile`, `docker-compose.yml`, actuator health endpoint in `application.yml`.
