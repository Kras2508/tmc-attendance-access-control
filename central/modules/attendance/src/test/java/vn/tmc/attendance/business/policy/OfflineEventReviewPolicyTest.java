package vn.tmc.attendance.business.policy;

import org.junit.jupiter.api.Test;
import vn.tmc.attendance.api.dto.RecordAttendanceCommand;
import vn.tmc.shared.id.EventId;
import vn.tmc.shared.type.Direction;
import vn.tmc.shared.type.EventMode;
import vn.tmc.shared.type.GateOutcome;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class OfflineEventReviewPolicyTest {

    private final ReviewPolicy policy = new OfflineEventReviewPolicy();

    @Test
    void offlineEventRequiresReview() {
        assertThat(policy.requiresReview(command(EventMode.OFFLINE))).isTrue();
    }

    @Test
    void onlineEventDoesNotRequireReview() {
        assertThat(policy.requiresReview(command(EventMode.ONLINE))).isFalse();
    }

    private static RecordAttendanceCommand command(EventMode mode) {
        return new RecordAttendanceCommand(EventId.random(), "E0001", "BD-G01", "BD",
                Direction.ENTRY, mode, Instant.parse("2026-11-09T00:29:58Z"), GateOutcome.PENDING);
    }
}
