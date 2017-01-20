package com.ubb.map.services.export;

/**
 * Created by marius on 1/19/2017.
 */
public enum ExportType {
    PDF("Pdf document"),
    CSV("Csv file"),
    JSON("JSON document"),
    XML("XML Document");

    private final String exportType;

    ExportType(String name) {
        exportType = name;
    }


    @Override
    public String toString() {
        return exportType + "( " + getExtensionPattern() + " )";
    }

    public String getExtension() {
        switch (this) {
            case PDF:
                return ".pdf";
            case CSV:
                return ".csv";
            case JSON:
                return ".json";
            case XML:
                return ".xml";
            default:
                return ".*";
        }
    }

    public String getExtensionPattern() {
        return "*" + getExtension();
    }
}
