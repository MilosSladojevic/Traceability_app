package com.example.Traceability.app.repository;

import com.example.Traceability.app.db.entity.SetupPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetupPieceRepository extends JpaRepository<SetupPiece,Long> {
}
