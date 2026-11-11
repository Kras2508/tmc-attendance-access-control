package vn.tmc.attendance.api;

import vn.tmc.attendance.api.dto.AttendanceRecord;
import vn.tmc.attendance.api.dto.RecordAttendanceCommand;
import vn.tmc.shared.id.EventId;
import vn.tmc.shared.type.Direction;
import vn.tmc.shared.type.GateOutcome;
import vn.tmc.shared.type.RecordResult;

import java.util.Optional;

public interface AttendanceRecordingApi {

    RecordResult record(RecordAttendanceCommand command);

    Optional<AttendanceRecord> find(EventId eventId);

    Optional<Direction> lastDirection(String employeeId);

    void updateGateOutcome(EventId eventId, GateOutcome outcome);
}
