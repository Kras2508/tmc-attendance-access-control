package vn.tmc.attendance.business.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.tmc.attendance.api.dto.RecordAttendanceCommand;
import vn.tmc.attendance.business.policy.ReviewPolicy;
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
    private static final ReviewPolicy NEVER_REVIEW = command -> false;
    private static final ReviewPolicy ALWAYS_REVIEW = command -> true;

    @Mock
    private AttendanceEventRepository repository;

    @Test
    void recordsANewEvent() {
        RecordAttendanceCommand command = command();
        when(repository.existsById(command.eventId().value())).thenReturn(false);

        RecordResult result = service(NEVER_REVIEW).record(command);

        assertThat(result).isEqualTo(RecordResult.ACCEPTED);
        AttendanceEventEntity saved = savedEvent();
        assertThat(saved.getId()).isEqualTo(command.eventId().value());
        assertThat(saved.getOccurredAt()).isEqualTo(SCANNED_AT);
        assertThat(saved.getReceivedAt()).isEqualTo(NOW);
        assertThat(saved.isReviewFlag()).isFalse();
    }

    @Test
    void sameEventDeliveredTwiceIsStoredOnce() {
        RecordAttendanceCommand command = command();
        when(repository.existsById(command.eventId().value())).thenReturn(true);

        RecordResult result = service(NEVER_REVIEW).record(command);

        assertThat(result).isEqualTo(RecordResult.DUPLICATE);
        verify(repository, never()).save(any());
    }

    @Test
    void storesTheDecisionOfTheReviewPolicy() {
        RecordAttendanceCommand command = command();
        when(repository.existsById(command.eventId().value())).thenReturn(false);

        service(ALWAYS_REVIEW).record(command);

        assertThat(savedEvent().isReviewFlag()).isTrue();
    }

    @Test
    void updatingTheOutcomeOfAnUnknownEventFails() {
        EventId unknown = EventId.random();
        when(repository.findById(unknown.value())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service(NEVER_REVIEW).updateGateOutcome(unknown, GateOutcome.NOT_OPENED))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private AttendanceRecordingService service(ReviewPolicy reviewPolicy) {
        return new AttendanceRecordingService(repository, () -> NOW, reviewPolicy);
    }

    private AttendanceEventEntity savedEvent() {
        ArgumentCaptor<AttendanceEventEntity> saved = ArgumentCaptor.forClass(AttendanceEventEntity.class);
        verify(repository).save(saved.capture());
        return saved.getValue();
    }

    private static RecordAttendanceCommand command() {
        return new RecordAttendanceCommand(EventId.random(), "E0001", "BD-G01", "BD",
                Direction.ENTRY, EventMode.ONLINE, SCANNED_AT, GateOutcome.PENDING);
    }
}
