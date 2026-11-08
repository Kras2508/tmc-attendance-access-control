package vn.tmc.shared.type;

/**
 * Per-event acknowledgement of a kiosk upload (E3). Table 25.
 */
public enum SyncStatusCode {
    ACCEPTED,
    DUPLICATE,
    REJECTED
}
