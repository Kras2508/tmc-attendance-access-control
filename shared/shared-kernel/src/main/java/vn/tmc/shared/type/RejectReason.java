package vn.tmc.shared.type;

/**
 * Reason a scan is rejected; written to the audit log (V1–V6, FR-7.3, NFR-3). Table 25.
 */
public enum RejectReason {
    EXPIRED,
    REPLAYED,
    BAD_SIGNATURE,
    REVOKED,
    PASSBACK,
    UNKNOWN_KIOSK
}
