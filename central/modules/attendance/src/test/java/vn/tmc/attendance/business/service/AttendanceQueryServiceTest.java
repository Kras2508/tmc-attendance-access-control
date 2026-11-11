package vn.tmc.attendance.business.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.tmc.attendance.api.dto.AttendanceRecord;
import vn.tmc.attendance.persistence.entity.AttendanceEventEntity;
import vn.tmc.attendance.persistence.repository.AttendanceEventRepository;
import vn.tmc.shared.id.EventId;
import vn.tmc.shared.type.Direction;
import vn.tmc.shared.type.EventMode;
import vn.tmc.shared.type.GateOutcome;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AttendanceQueryServiceTest {

    private static final UUID EVENT_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final Instant SCANNED_AT = Instant.parse("2026-11-09T00:55:00Z");
    private static final Instant RECEIVED_AT = Instant.parse("2026-11-09T00:55:01Z");

    @Mock
    private AttendanceEventRepository repository;

    private AttendanceQueryService service;

    @BeforeEach
    void setUp() {
        service = new AttendanceQueryService(repository);
    }

    @Test
    void monthIsBoundedByLocalMidnightOfTheFactoryTimeZone() {
        service.findByEmployee("E0001", YearMonth.of(2026, 11));

        verify(repository).findByEmployeeInPeriod("E0001",
                Instant.parse("2026-10-31T17:00:00Z"),
                Instant.parse("2026-11-30T17:00:00Z"));
    }

    @Test
    void dateRangeIncludesTheWholeLastDay() {
        service.findByFactory("BD", LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 30));

        verify(repository).findByFactoryInPeriod("BD",
                Instant.parse("2026-10-31T17:00:00Z"),
                Instant.parse("2026-11-30T17:00:00Z"));
    }

    @Test
    void mapsAnEventToARecordWithoutExposingTheEntity() {
        when(repository.findByEmployeeInPeriod("E0001",
                Instant.parse("2026-10-31T17:00:00Z"),
                Instant.parse("2026-11-30T17:00:00Z"))).thenReturn(List.of(event()));

        List<AttendanceRecord> records = service.findByEmployee("E0001", YearMonth.of(2026, 11));

        assertThat(records).containsExactly(new AttendanceRecord(new EventId(EVENT_ID), "E0001", "BD-G01", "BD",
                Direction.ENTRY, EventMode.OFFLINE, SCANNED_AT, RECEIVED_AT, GateOutcome.OPENED_LOCAL, true));
    }

    @Test
    void lastDirectionIsTakenFromTheLatestEvent() {
        when(repository.findFirstByEmployeeIdOrderByOccurredAtDesc("E0001")).thenReturn(Optional.of(event()));

        assertThat(service.lastDirection("E0001")).contains(Direction.ENTRY);
    }

    @Test
    void employeeWithoutEventsHasNoLastDirection() {
        when(repository.findFirstByEmployeeIdOrderByOccurredAtDesc("E0009")).thenReturn(Optional.empty());

        assertThat(service.lastDirection("E0009")).isEmpty();
    }

    private static AttendanceEventEntity event() {
        return AttendanceEventEntity.newEvent(EVENT_ID, "E0001", "BD-G01", "BD", Direction.ENTRY,
                EventMode.OFFLINE, SCANNED_AT, RECEIVED_AT, GateOutcome.OPENED_LOCAL, true);
    }
}
