package vn.tmc.shared.type;

/**
 * Result of recording an attendance event idempotently (E1). Table 25.
 */
public enum RecordResult {
    ACCEPTED,
    DUPLICATE
}
