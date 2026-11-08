package vn.tmc.identityqr.persistence.repository;

import vn.tmc.qrtoken.spi.NonceRepository;

/**
 * Server NonceRepository on identity_qr.used_nonce (PK = nonce, 60 s retention).
 */
public class DbNonceRepository implements NonceRepository {
    // TODO(M2): implement — see Javadoc for the source in the report.
}
