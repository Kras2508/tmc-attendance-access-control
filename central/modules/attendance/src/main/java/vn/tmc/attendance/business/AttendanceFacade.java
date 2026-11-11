package vn.tmc.attendance.business;

import org.springframework.stereotype.Component;
import vn.tmc.attendance.api.AttendanceQueryApi;
import vn.tmc.attendance.api.AttendanceRecordingApi;
import vn.tmc.attendance.api.dto.AttendanceRecord;
import vn.tmc.attendance.api.dto.RecordAttendanceCommand;
import vn.tmc.attendance.business.service.AttendanceQueryService;
import vn.tmc.attendance.business.service.AttendanceRecordingService;
import vn.tmc.shared.id.EventId;
import vn.tmc.shared.type.Direction;
import vn.tmc.shared.type.GateOutcome;
import vn.tmc.shared.type.RecordResult;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Component
public class AttendanceFacade implements AttendanceRecordingApi, AttendanceQueryApi {

    private final AttendanceRecordingService recordingService;
    private final AttendanceQueryService queryService;

    public AttendanceFacade(AttendanceRecordingService recordingService, AttendanceQueryService queryService) {
        this.recordingService = recordingService;
        this.queryService = queryService;
    }

    @Override
    public RecordResult record(RecordAttendanceCommand command) {
        return recordingService.record(command);
    }

    @Override
    public Optional<AttendanceRecord> find(EventId eventId) {
        return queryService.find(eventId);
    }

    @Override
    public Optional<Direction> lastDirection(String employeeId) {
        return queryService.lastDirection(employeeId);
    }

    @Override
    public void updateGateOutcome(EventId eventId, GateOutcome outcome) {
        recordingService.updateGateOutcome(eventId, outcome);
    }

    @Override
    public List<AttendanceRecord> findByEmployee(String employeeId, YearMonth month) {
        return queryService.findByEmployee(employeeId, month);
    }

    @Override
    public List<AttendanceRecord> findByFactory(String factoryId, LocalDate from, LocalDate toInclusive) {
        return queryService.findByFactory(factoryId, from, toInclusive);
    }
}
