package vn.tmc.attendance.presentation;

import vn.tmc.attendance.api.dto.AttendanceRecord;
import vn.tmc.shared.type.Direction;
import vn.tmc.shared.type.EventMode;
import vn.tmc.shared.type.GateOutcome;

import java.time.Instant;

/**
 * JSON shape of an attendance record returned by the REST API.
 */
public record AttendanceResponse(
        String eventId,
        String employeeId,
        String gateStationId,
        String factoryId,
        Direction direction,
        EventMode mode,
        Instant occurredAt,
        Instant receivedAt,
        GateOutcome gateOutcome,
        boolean reviewFlag) {

    static AttendanceResponse from(AttendanceRecord r) {
        return new AttendanceResponse(r.eventId().toString(), r.employeeId(), r.gateStationId(), r.factoryId(),
                r.direction(), r.mode(), r.occurredAt(), r.receivedAt(), r.gateOutcome(), r.reviewFlag());
    }
}
