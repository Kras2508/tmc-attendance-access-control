package vn.tmc.attendance.presentation;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.tmc.attendance.business.AttendanceFacade;
import vn.tmc.shared.id.EventId;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

/**
 * REST: authorized users retrieve attendance records within their scope (FR-3.4).
 */
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceFacade facade;

    public AttendanceController(AttendanceFacade facade) {
        this.facade = facade;
    }

    /** Example: GET /api/attendance/employees/E0001?month=2026-11 */
    @GetMapping("/employees/{employeeId}")
    public List<AttendanceResponse> byEmployee(@PathVariable String employeeId,
                                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        return facade.findByEmployee(employeeId, month).stream().map(AttendanceResponse::from).toList();
    }

    /** Example: GET /api/attendance/factories/BD?from=2026-11-01&to=2026-11-30 */
    @GetMapping("/factories/{factoryId}")
    public List<AttendanceResponse> byFactory(@PathVariable String factoryId,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return facade.findByFactory(factoryId, from, to).stream().map(AttendanceResponse::from).toList();
    }

    /** Example: GET /api/attendance/events/3f1c...; 404 if unknown. */
    @GetMapping("/events/{eventId}")
    public ResponseEntity<AttendanceResponse> byEventId(@PathVariable String eventId) {
        return facade.find(EventId.of(eventId))
                .map(AttendanceResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
