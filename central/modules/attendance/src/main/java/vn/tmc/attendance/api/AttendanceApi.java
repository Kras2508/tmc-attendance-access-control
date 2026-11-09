package vn.tmc.attendance.api;

import vn.tmc.attendance.api.dto.AttendanceRecord;
import vn.tmc.attendance.api.dto.RecordAttendanceCommand;
import vn.tmc.shared.id.EventId;
import vn.tmc.shared.type.Direction;
import vn.tmc.shared.type.GateOutcome;
import vn.tmc.shared.type.RecordResult;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface AttendanceApi {

    /** Records one entry/exit; returns DUPLICATE if the eventId is already stored (E1). Joins the caller's transaction. */
    RecordResult record(RecordAttendanceCommand command);

    /** Looks up an event by its kiosk-generated id; used to answer a retried scan idempotently (§8.1.1 step 2). */
    Optional<AttendanceRecord> find(EventId eventId);

    /** Direction of the employee's latest event, for anti-passback V6. */
    Optional<Direction> lastDirection(String employeeId);

    /** Stores the physical gate outcome after the turnstile call (E5, FR-3.5). */
    void updateGateOutcome(EventId eventId, GateOutcome outcome);

    /** Events of one employee in one calendar month (FR-3.4, FR-4.6). */
    List<AttendanceRecord> findByEmployee(String employeeId, YearMonth month);

    /** Events of one factory between two dates, both inclusive (FR-3.4, FR-4.1). */
    List<AttendanceRecord> findByFactory(String factoryId, LocalDate from, LocalDate toInclusive);
}
