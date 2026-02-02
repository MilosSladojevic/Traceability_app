package com.example.Traceability.app.service;

import com.example.Traceability.app.db.entity.Measure;
import com.example.Traceability.app.db.entity.Reference;
import com.example.Traceability.app.db.help.MeasureFileModel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

@Service
public class MeasureService {

    private final ObjectMapper objectMapper = new ObjectMapper();


    public List<MeasureFileModel> loadFromJson(){
        try {
            InputStream is = new ClassPathResource("/JSON/measure.json").getInputStream();
            return objectMapper.readValue(is, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load measures.json", e);
        }

    }
}
