package vn.tmc.kiosk.infrastructure;

import vn.tmc.qrtoken.spi.CredentialStatusSource;
import vn.tmc.qrtoken.spi.SigningKeyProvider;

/**
 * Kiosk cache (cached_signing_key, revoked_credential, cache_meta); implements only the two SPIs it needs (ISP).
 */
public class LocalCache implements SigningKeyProvider, CredentialStatusSource {
    // TODO(M2): implement — see Javadoc for the source in the report.
}
