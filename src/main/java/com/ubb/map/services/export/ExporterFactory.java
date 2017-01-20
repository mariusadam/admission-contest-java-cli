package com.ubb.map.services.export;

import com.ubb.map.domain.Candidate;
import com.ubb.map.domain.Department;
import com.ubb.map.domain.Option;
import com.ubb.map.services.export.csv.CandidateCsvExporter;
import com.ubb.map.services.export.csv.DepartmentCsvExporter;
import com.ubb.map.services.export.csv.OptionCsvExporter;
import com.ubb.map.services.export.json.BaseJsonExporter;
import com.ubb.map.services.export.pdf.CandidatePdfExporter;
import com.ubb.map.services.export.pdf.DepartmentPdfExporter;
import com.ubb.map.services.export.pdf.OptionPdfExporter;
import com.ubb.map.services.export.xml.BaseXmlExporter;
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
            } else if (entity.getName().equals(Department.class.getName())) {
                return new DepartmentPdfExporter((List<Department>) items, fileName);
            } else if (entity.getName().equals(Option.class.getName())) {
                return new OptionPdfExporter((List<Option>) items, fileName);
            }

        } else if (fileName.endsWith(ExportType.CSV.getExtension())) {
            if (entity.getName().equals(Candidate.class.getName())) {
                return new CandidateCsvExporter((List<Candidate>) items, fileName);
            } else if (entity.getName().equals(Department.class.getName())) {
                return new DepartmentCsvExporter((List<Department>) items, fileName);
            } else if (entity.getName().equals(Option.class.getName())) {
                return new OptionCsvExporter((List<Option>) items, fileName);
            }
        } else if (fileName.endsWith(ExportType.XML.getExtension())) {
            return new BaseXmlExporter<>(items, fileName);
        } else if (fileName.endsWith(ExportType.JSON.getExtension())) {
            return new BaseJsonExporter(items, fileName);
        }

        throw new IllegalArgumentException("Could not find an exporter for file " + fileName);
    }
}
