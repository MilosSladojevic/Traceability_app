package com.example.Traceability.app.repository;

import com.example.Traceability.app.db.entity.RfPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RfPieceRepository extends JpaRepository<RfPiece, Long> {

    Optional<RfPiece> findByQrCode(String qrCode);
}

