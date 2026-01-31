package com.example.Traceability.app.repository;

import com.example.Traceability.app.db.entity.ControlledPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControlledPieceRepository extends JpaRepository<ControlledPiece,Long> {
}
