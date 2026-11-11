package vn.tmc.attendance.business.policy;

import org.springframework.stereotype.Component;
import vn.tmc.attendance.api.dto.RecordAttendanceCommand;
import vn.tmc.shared.type.EventMode;

@Component
public class OfflineEventReviewPolicy implements ReviewPolicy {

    @Override
    public boolean requiresReview(RecordAttendanceCommand command) {
        return command.mode() == EventMode.OFFLINE;
    }
}
