package com.ubb.map.services.export;

import com.ubb.map.domain.Candidate;
import com.ubb.map.services.export.pdf.CandidatePdfExporter;
import javafx.concurrent.Task;

import java.util.List;

/**
 * Created by marius on 1/19/2017.
 */
@SuppressWarnings("unchecked")
public class ExporterFactory {
    public static <T> Task createForFileName(String fileName, List<T> items, Class<T> entity) {
        if (fileName.endsWith(ExportType.PDF.getExtension())) {
            if (entity.getName().equals(Candidate.class.getName())) {
                return new CandidatePdfExporter((List<Candidate>) items, fileName);
            }

            return null;
        } else {
            return null;
        }
    }
}
