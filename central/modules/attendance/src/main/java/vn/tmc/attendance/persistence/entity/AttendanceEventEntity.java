package vn.tmc.attendance.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.springframework.data.domain.Persistable;
import vn.tmc.shared.type.Direction;
import vn.tmc.shared.type.EventMode;
import vn.tmc.shared.type.GateOutcome;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(schema = "attendance", name = "attendance_event")
public class AttendanceEventEntity implements Persistable<UUID> {
    @Id
    @Column(name = "event_id")
    private UUID eventId;

    @Column(name = "employee_id", nullable = false, length = 32)
    private String employeeId;

    @Column(name = "gate_station_id", nullable = false, length = 32)
    private String gateStationId;

    @Column(name = "factory_id", nullable = false, length = 32)
    private String factoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false, length = 5)
    private Direction direction;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode", nullable = false, length = 7)
    private EventMode mode;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    @Column(name = "received_at", nullable = false)
    private Instant receivedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "gate_outcome", nullable = false, length = 12)
    private GateOutcome gateOutcome;

    @Column(name = "review_flag", nullable = false)
    private boolean reviewFlag;

    @Transient
    private boolean newEntity;

    protected AttendanceEventEntity() {
        // required by JPA
    }

    public static AttendanceEventEntity newEvent(UUID eventId, String employeeId, String gateStationId,
                                                 String factoryId, Direction direction, EventMode mode,
                                                 Instant occurredAt, Instant receivedAt, GateOutcome gateOutcome) {
        AttendanceEventEntity event = new AttendanceEventEntity();
        event.eventId = eventId;
        event.employeeId = employeeId;
        event.gateStationId = gateStationId;
        event.factoryId = factoryId;
        event.direction = direction;
        event.mode = mode;
        event.occurredAt = occurredAt;
        event.receivedAt = receivedAt;
        event.gateOutcome = gateOutcome;
        event.reviewFlag = mode == EventMode.OFFLINE;   // every offline event is reviewed (E6)
        event.newEntity = true;
        return event;
    }

    public void changeGateOutcome(GateOutcome outcome) {
        this.gateOutcome = outcome;
    }

    @PostLoad
    @PostPersist
    void markNotNew() {
        this.newEntity = false;
    }

    @Override
    public UUID getId() {
        return eventId;
    }

    @Override
    public boolean isNew() {
        return newEntity;
    }

    public String getEmployeeId() { return employeeId; }
    public String getGateStationId() { return gateStationId; }
    public String getFactoryId() { return factoryId; }
    public Direction getDirection() { return direction; }
    public EventMode getMode() { return mode; }
    public Instant getOccurredAt() { return occurredAt; }
    public Instant getReceivedAt() { return receivedAt; }
    public GateOutcome getGateOutcome() { return gateOutcome; }
    public boolean isReviewFlag() { return reviewFlag; }
}
