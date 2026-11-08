/**
 * <b>Identity &amp; QR</b> module — domain module of the TMC modular monolith (ADR-01, §3.3.1).
 * <p>Owned requirements: FR-1.1 – FR-1.5, FR-7.1 – FR-7.3, FR-7.5 (keys/revocation list).
 * <p>May depend on: Access Management (AuthorizationApi), Audit (AuditApi), qr-token-lib, shared kernel.
 *
 * <p>Layering (dependency rule 4): presentation → business → persistence. Other modules see only {@code api}.
 */
package vn.tmc.identityqr;
