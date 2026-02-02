package com.example.Traceability.app.repository;

import com.example.Traceability.app.db.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {

    @Query("""
    SELECT s FROM Session s
    WHERE s.startOfSession <= :endOfDay
    AND s.endOfSession >= :startOfDay
""")
    List<Session> findSessionsForDay(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

//    List<Session> findByStartOfSessionLessThanEqualAndEndOfSessionIsNullOrEndOfSessionGreaterThanEqual(
//            LocalDateTime startOfDay,LocalDateTime endOfDay
//
//    );
//


//    List<Session> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}
