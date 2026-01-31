package com.example.Traceability.app.repository;

import com.example.Traceability.app.db.entity.RfPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RfPieceRepository extends JpaRepository<RfPiece, Long> {

}

