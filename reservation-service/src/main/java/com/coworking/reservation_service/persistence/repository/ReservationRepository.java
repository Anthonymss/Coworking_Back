package com.coworking.reservation_service.persistence.repository;

import com.coworking.reservation_service.persistence.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Query("SELECT r FROM Reservation r WHERE r.spaceId = :spaceId AND DATE(r.startDate) = :date")
    List<Reservation> findBySpaceIdAndDate(@Param("spaceId") Long spaceId, @Param("date") LocalDate date);

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.spaceId = :spaceId AND " +
            "(r.startDate < :endDate AND r.endDate > :startDate)")
    boolean existsBySpaceIdAndTimeRange(@Param("spaceId") Long spaceId,
                                        @Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);

}
