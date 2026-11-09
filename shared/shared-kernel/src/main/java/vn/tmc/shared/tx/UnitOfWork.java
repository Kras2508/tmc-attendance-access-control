package vn.tmc.shared.tx;

import java.util.function.Supplier;

/**
 * Shared transaction abstraction (§8.1.1, ADR-02).
 * <p>Gate Access executes the attendance write, the audit write and the nonce write inside one
 */
public interface UnitOfWork {
    <T> T execute(Supplier<T> work);
}
