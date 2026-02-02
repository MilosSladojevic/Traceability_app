package com.example.Traceability.app.service;


import com.example.Traceability.app.db.entity.Reference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;



import java.io.InputStream;
import java.util.List;

@Service
public class ReferenceService {

   private final ObjectMapper objectMapper = new ObjectMapper();


    public List<Reference>loadFromJson(){
        try {
            InputStream is = new ClassPathResource("/JSON/Reference.json").getInputStream();
            return objectMapper.readValue(is, new TypeReference<List<Reference>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to load references.json", e);
        }

    }
}
