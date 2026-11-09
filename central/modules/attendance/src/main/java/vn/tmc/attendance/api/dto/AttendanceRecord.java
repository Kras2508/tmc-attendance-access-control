package vn.tmc.attendance.api.dto;

import vn.tmc.shared.id.EventId;
import vn.tmc.shared.type.Direction;
import vn.tmc.shared.type.EventMode;
import vn.tmc.shared.type.GateOutcome;

import java.time.Instant;

/**
 * Read model of one attendance transaction returned to other modules (FR-3.4).
 */
public record AttendanceRecord(
        EventId eventId,
        String employeeId,
        String gateStationId,
        String factoryId,
        Direction direction,
        EventMode mode,
        Instant occurredAt,
        Instant receivedAt,
        GateOutcome gateOutcome,
        boolean reviewFlag) {
}
