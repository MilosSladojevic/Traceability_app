package com.example.Traceability.app.repository;

import com.example.Traceability.app.db.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    List<Session> findTop100ByOrderByIdDesc();

//    @Query("SELECT s FROM Session s WHERE noOfOutbox = :ticketId")
//    List<Session> findByNoOfOutbox(@Param("ticketId") String ticketId);

    @Query("SELECT s FROM Session s WHERE s.noOutbox = :val")
    List<Session> findByNoOutbox(@Param("val") String val);
}
