package vn.tmc.shared.type;

/**
 * Physical outcome of the gate-opening request (E5, FR-3.5). Table 25.
 */
public enum GateOutcome {
    PENDING,
    OPENED,
    OPENED_LOCAL,
    NOT_OPENED,
    FAILED
}
