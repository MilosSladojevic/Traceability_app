package com.example.Traceability.app.db.help;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class MeasureFileModel {
    private String type;
    private Double nominal;
    private Double minTolerance;
    private Double maxTolerance;
    private List<String> references;
}

