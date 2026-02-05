package com.example.Traceability.app.seeder;

import com.example.Traceability.app.db.entity.Reference;
import com.example.Traceability.app.repository.ReferenceRepository;
import com.example.Traceability.app.service.ReferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {


    private final ReferenceRepository referenceRepository;
    private final ReferenceService referenceService;




    @Override
    public void run(String... args) throws Exception {
        boolean referenceFool = referenceRepository.count()>0;

        if (!referenceFool){
            List<Reference> references = referenceService.loadFromJson();

            for (Reference ref : references){
                referenceRepository.save(ref);
            }
        }

    }
}
