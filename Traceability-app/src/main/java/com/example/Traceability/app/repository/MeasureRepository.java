package com.example.Traceability.app.repository;

import com.example.Traceability.app.db.entity.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasureRepository extends JpaRepository<Measure,Long> {
}
