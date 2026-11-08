package vn.tmc.shared.type;

/**
 * Status of a device-bound QR credential (§6.2.4). Table 25.
 */
public enum CredentialStatus {
    ACTIVE,
    REVOKED,
    EXPIRED
}
