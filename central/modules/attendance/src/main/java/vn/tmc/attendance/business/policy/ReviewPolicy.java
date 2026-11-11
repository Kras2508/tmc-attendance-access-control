package vn.tmc.attendance.business.policy;

import vn.tmc.attendance.api.dto.RecordAttendanceCommand;

public interface ReviewPolicy {

    boolean requiresReview(RecordAttendanceCommand command);
}
