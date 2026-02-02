package com.example.Traceability.app.seeder;

import com.example.Traceability.app.db.entity.Measure;
import com.example.Traceability.app.db.entity.Reference;
import com.example.Traceability.app.db.help.MeasureFileModel;
import com.example.Traceability.app.repository.MeasureRepository;
import com.example.Traceability.app.repository.ReferenceRepository;
import com.example.Traceability.app.service.MeasureService;
import com.example.Traceability.app.service.ReferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReferenceSeeder implements CommandLineRunner {


    private final ReferenceRepository referenceRepository;
    private final ReferenceService referenceService;
    private final MeasureRepository measureRepository;
    private final MeasureService measureService;


    @Override
    public void run(String... args) throws Exception {
        boolean referenceFool = referenceRepository.count()>0;
        boolean measureFool = measureRepository.count()>0;

        if (!referenceFool){
            List<Reference> references = referenceService.loadFromJson();

            for (Reference ref : references){
                referenceRepository.save(ref);
            }
        }

        if (!measureFool) {



            List<MeasureFileModel> models = measureService.loadFromJson();

            for (MeasureFileModel fm : models) {
                Measure measure = new Measure();
                measure.setType(fm.getType());
                measure.setNominal(fm.getNominal());
                measure.setMinTolerance(fm.getMinTolerance());
                measure.setMaxTolerance(fm.getMaxTolerance());
                measure.setRealValue(0.0);
                measure.setReferences(new ArrayList<>());


                List<Reference> references = referenceRepository.findByReferenceNumberIn(fm.getReferences());

                for (Reference ref : references) {
                    measure.getReferences().add(ref);

                }

                measureRepository.save(measure);


            }
        }

    }
}
