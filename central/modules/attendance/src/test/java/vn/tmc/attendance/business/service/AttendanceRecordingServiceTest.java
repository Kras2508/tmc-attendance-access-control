package vn.tmc.attendance.business.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.tmc.attendance.api.dto.RecordAttendanceCommand;
import vn.tmc.attendance.persistence.entity.AttendanceEventEntity;
import vn.tmc.attendance.persistence.repository.AttendanceEventRepository;
import vn.tmc.shared.id.EventId;
import vn.tmc.shared.type.Direction;
import vn.tmc.shared.type.EventMode;
import vn.tmc.shared.type.GateOutcome;
import vn.tmc.shared.type.RecordResult;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AttendanceRecordingServiceTest {

    private static final Instant NOW = Instant.parse("2026-11-09T00:30:00Z");
    private static final Instant SCANNED_AT = Instant.parse("2026-11-09T00:29:58Z");

    @Mock
    private AttendanceEventRepository repository;

    private AttendanceRecordingService service;

    @BeforeEach
    void setUp() {
        service = new AttendanceRecordingService(repository, () -> NOW);
    }

    @Test
    void recordsANewEvent() {
        RecordAttendanceCommand command = command(EventMode.ONLINE);
        when(repository.existsById(command.eventId().value())).thenReturn(false);

        RecordResult result = service.record(command);

        assertThat(result).isEqualTo(RecordResult.ACCEPTED);
        ArgumentCaptor<AttendanceEventEntity> saved = ArgumentCaptor.forClass(AttendanceEventEntity.class);
        verify(repository).save(saved.capture());
        assertThat(saved.getValue().getId()).isEqualTo(command.eventId().value());
        assertThat(saved.getValue().getOccurredAt()).isEqualTo(SCANNED_AT);
        assertThat(saved.getValue().getReceivedAt()).isEqualTo(NOW);
        assertThat(saved.getValue().isReviewFlag()).isFalse();
    }

    @Test
    void sameEventDeliveredTwiceIsStoredOnce() {
        RecordAttendanceCommand command = command(EventMode.ONLINE);
        when(repository.existsById(command.eventId().value())).thenReturn(true);

        RecordResult result = service.record(command);

        assertThat(result).isEqualTo(RecordResult.DUPLICATE);
        verify(repository, never()).save(any());
    }

    @Test
    void offlineEventIsFlaggedForReview() {
        RecordAttendanceCommand command = command(EventMode.OFFLINE);
        when(repository.existsById(command.eventId().value())).thenReturn(false);

        service.record(command);

        ArgumentCaptor<AttendanceEventEntity> saved = ArgumentCaptor.forClass(AttendanceEventEntity.class);
        verify(repository).save(saved.capture());
        assertThat(saved.getValue().isReviewFlag()).isTrue();
    }

    @Test
    void updatingTheOutcomeOfAnUnknownEventFails() {
        EventId unknown = EventId.random();
        when(repository.findById(unknown.value())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateGateOutcome(unknown, GateOutcome.NOT_OPENED))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static RecordAttendanceCommand command(EventMode mode) {
        return new RecordAttendanceCommand(EventId.random(), "E0001", "BD-G01", "BD",
                Direction.ENTRY, mode, SCANNED_AT, GateOutcome.PENDING);
    }
}
