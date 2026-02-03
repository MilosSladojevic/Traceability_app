package com.example.Traceability.app.repository;

import com.example.Traceability.app.db.entity.RusPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RusPieceRepository extends JpaRepository<RusPiece,Long> {
    Optional<RusPiece> findByQrCode(String qrCode);
}
