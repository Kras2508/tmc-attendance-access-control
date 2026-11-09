package vn.tmc.attendance.api.dto;

import vn.tmc.shared.id.EventId;
import vn.tmc.shared.type.Direction;
import vn.tmc.shared.type.EventMode;
import vn.tmc.shared.type.GateOutcome;

import java.time.Instant;
import java.util.Objects;

/**
 * @param occurredAt kiosk time of the scan (F7)
 */
public record RecordAttendanceCommand(
        EventId eventId,
        String employeeId,
        String gateStationId,
        String factoryId,
        Direction direction,
        EventMode mode,
        Instant occurredAt,
        GateOutcome gateOutcome) {

    public RecordAttendanceCommand {
        Objects.requireNonNull(eventId, "eventId");
        Objects.requireNonNull(employeeId, "employeeId");
        Objects.requireNonNull(gateStationId, "gateStationId");
        Objects.requireNonNull(factoryId, "factoryId");
        Objects.requireNonNull(direction, "direction");
        Objects.requireNonNull(mode, "mode");
        Objects.requireNonNull(occurredAt, "occurredAt");
        Objects.requireNonNull(gateOutcome, "gateOutcome");
    }
}
