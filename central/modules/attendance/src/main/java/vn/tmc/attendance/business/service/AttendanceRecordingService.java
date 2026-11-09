package vn.tmc.attendance.business.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tmc.attendance.api.dto.RecordAttendanceCommand;
import vn.tmc.attendance.persistence.entity.AttendanceEventEntity;
import vn.tmc.attendance.persistence.repository.AttendanceEventRepository;
import vn.tmc.shared.id.EventId;
import vn.tmc.shared.time.Clock;
import vn.tmc.shared.type.GateOutcome;
import vn.tmc.shared.type.RecordResult;

import java.util.UUID;

@Service
public class AttendanceRecordingService {

    private final AttendanceEventRepository repository;
    private final Clock clock;

    public AttendanceRecordingService(AttendanceEventRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    @Transactional
    public RecordResult record(RecordAttendanceCommand command) {
        UUID id = command.eventId().value();
        if (repository.existsById(id)) {
            return RecordResult.DUPLICATE;
        }
        repository.save(AttendanceEventEntity.newEvent(
                id,
                command.employeeId(),
                command.gateStationId(),
                command.factoryId(),
                command.direction(),
                command.mode(),
                command.occurredAt(),
                clock.now(),
                command.gateOutcome()));
        return RecordResult.ACCEPTED;
    }

    /** Called after the turnstile call (E5); the event is never deleted, only its outcome changes (ADR-02). */
    @Transactional
    public void updateGateOutcome(EventId eventId, GateOutcome outcome) {
        AttendanceEventEntity event = repository.findById(eventId.value())
                .orElseThrow(() -> new IllegalArgumentException("Unknown attendance event " + eventId));
        event.changeGateOutcome(outcome);
    }
}
