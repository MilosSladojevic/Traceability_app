package com.example.Traceability.app.repository;

import com.example.Traceability.app.db.entity.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference,Long> {
    List<Reference> findByReferenceNumberIn(List<String> referenceNumbers);

    Reference findByReferenceNumber(String refNumber);
}
