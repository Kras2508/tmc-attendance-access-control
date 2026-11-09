package vn.tmc.attendance.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.tmc.attendance.persistence.entity.AttendanceEventEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Persists attendance events; PK event_id gives idempotency (E1). Only this module writes them (FR-3.3).
 */
public interface AttendanceEventRepository extends JpaRepository<AttendanceEventEntity, UUID> {

    Optional<AttendanceEventEntity> findFirstByEmployeeIdOrderByOccurredAtDesc(String employeeId);

    @Query("""
            select e from AttendanceEventEntity e
            where e.employeeId = :employeeId and e.occurredAt >= :from and e.occurredAt < :to
            order by e.occurredAt
            """)
    List<AttendanceEventEntity> findByEmployeeInPeriod(@Param("employeeId") String employeeId,
                                                       @Param("from") Instant from,
                                                       @Param("to") Instant to);

    @Query("""
            select e from AttendanceEventEntity e
            where e.factoryId = :factoryId and e.occurredAt >= :from and e.occurredAt < :to
            order by e.occurredAt
            """)
    List<AttendanceEventEntity> findByFactoryInPeriod(@Param("factoryId") String factoryId,
                                                      @Param("from") Instant from,
                                                      @Param("to") Instant to);
}
