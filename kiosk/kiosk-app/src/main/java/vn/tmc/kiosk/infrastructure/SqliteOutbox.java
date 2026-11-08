package vn.tmc.kiosk.infrastructure;

import vn.tmc.kiosk.port.Outbox;

/**
 * Outbox on SQLite table outbox_event (PK event_id), WAL + synchronous=FULL.
 */
public class SqliteOutbox implements Outbox {
    // TODO(M2): implement — see Javadoc for the source in the report.
}
