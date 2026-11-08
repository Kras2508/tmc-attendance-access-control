package vn.tmc.shared.tx;

/**
 * Shared transaction abstraction (§8.1.1, ADR-02).
 * <p>Gate Access executes the attendance write, the audit write and the nonce write inside one
 * {@code execute(...)} call so they commit atomically; each owning module still performs its own write.
 */
public interface UnitOfWork {
    // TODO(M2): implement — see Javadoc for the source in the report.
}
