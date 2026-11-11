package vn.tmc.attendance.api;

import vn.tmc.attendance.api.dto.AttendanceRecord;
import vn.tmc.shared.id.EventId;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface AttendanceQueryApi {

    Optional<AttendanceRecord> find(EventId eventId);

    List<AttendanceRecord> findByEmployee(String employeeId, YearMonth month);

    List<AttendanceRecord> findByFactory(String factoryId, LocalDate from, LocalDate toInclusive);
}
