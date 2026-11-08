package vn.tmc.qrtoken.spi;

/**
 * Stores used nonces; returns false on a reused nonce. Server: DbNonceRepository; kiosk: LocalNonceStore. (ISP: small interfaces, Table 26.)
 */
public interface NonceRepository {
    // TODO(M2): implement — see Javadoc for the source in the report.
}
