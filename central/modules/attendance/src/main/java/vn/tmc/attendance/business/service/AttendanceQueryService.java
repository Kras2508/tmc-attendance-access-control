package vn.tmc.attendance.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tmc.attendance.api.dto.AttendanceRecord;
import vn.tmc.attendance.persistence.entity.AttendanceEventEntity;
import vn.tmc.attendance.persistence.repository.AttendanceEventRepository;
import vn.tmc.shared.id.EventId;
import vn.tmc.shared.type.Direction;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

/**
 * Retrieves events by employee, period and factory (FR-3.4).
 */
@Service
@Transactional(readOnly = true)
public class AttendanceQueryService {

    /** Both factories (Binh Duong, Long An) are in this time zone; month boundaries are local time. */
    static final ZoneId FACTORY_ZONE = ZoneId.of("Asia/Ho_Chi_Minh");

    private final AttendanceEventRepository repository;

    public AttendanceQueryService(AttendanceEventRepository repository) {
        this.repository = repository;
    }

    public Optional<AttendanceRecord> find(EventId eventId) {
        return repository.findById(eventId.value()).map(AttendanceQueryService::toRecord);
    }

    public Optional<Direction> lastDirection(String employeeId) {
        return repository.findFirstByEmployeeIdOrderByOccurredAtDesc(employeeId)
                .map(AttendanceEventEntity::getDirection);
    }

    public List<AttendanceRecord> findByEmployee(String employeeId, YearMonth month) {
        return repository.findByEmployeeInPeriod(employeeId, startOf(month.atDay(1)), startOf(month.plusMonths(1).atDay(1)))
                .stream().map(AttendanceQueryService::toRecord).toList();
    }

    public List<AttendanceRecord> findByFactory(String factoryId, LocalDate from, LocalDate toInclusive) {
        return repository.findByFactoryInPeriod(factoryId, startOf(from), startOf(toInclusive.plusDays(1)))
                .stream().map(AttendanceQueryService::toRecord).toList();
    }

    private static Instant startOf(LocalDate day) {
        return day.atStartOfDay(FACTORY_ZONE).toInstant();
    }

    private static AttendanceRecord toRecord(AttendanceEventEntity e) {
        return new AttendanceRecord(
                new EventId(e.getId()),
                e.getEmployeeId(),
                e.getGateStationId(),
                e.getFactoryId(),
                e.getDirection(),
                e.getMode(),
                e.getOccurredAt(),
                e.getReceivedAt(),
                e.getGateOutcome(),
                e.isReviewFlag());
    }
}
