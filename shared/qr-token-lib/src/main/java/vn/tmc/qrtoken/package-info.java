/**
 * QR TOKEN LIBRARY (§8.1.2) — parser, validator and checks V1–V5 for the device-bound rotating QR (§6.2.4).
 * <p>Reused unchanged by the central application (Identity &amp; QR) and by the kiosk (OfflineValidator);
 * only the {@code spi} implementations differ (LSP, Table 26). Depends only on the shared kernel.
 */
package vn.tmc.qrtoken;
