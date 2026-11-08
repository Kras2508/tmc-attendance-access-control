/**
 * GATE KIOSK application (edge quantum). Layers (hexagonal):
 * presentation → application → port ← infrastructure; domain is used by all.
 * <p>Depends only on the shared kernel and qr-token-lib — NEVER on central modules (it talks to them over ScanApi/HTTPS).
 */
package vn.tmc.kiosk;
