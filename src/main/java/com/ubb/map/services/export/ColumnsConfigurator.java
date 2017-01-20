package com.ubb.map.services.export;

import com.ubb.map.domain.Candidate;
import com.ubb.map.domain.Department;
import com.ubb.map.domain.Entity;
import com.ubb.map.domain.Option;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marius on 1/19/2017.
 */
public class ColumnsConfigurator {
    public static List<Callback<Entity, String>> getEntityColumnsCallBacks() {
        List<Callback<Entity, String>> result = new ArrayList<>();
        result.add(0, param -> param.getId().toString());
        result.add(4, param -> param.getCreatedAt().toString());
        result.add(5, param -> param.getUpdatedAt().toString());

        return result;
    }

    public static List<Callback<Candidate, String>> getCandidateColumnsCallBacks() {
        List<Callback<Candidate, String>> result = new ArrayList<>();
        result.add(0, param -> param.getId().toString());
        result.add(1, Candidate::getName);
        result.add(2, Candidate::getPhone);
        result.add(3, Candidate::getAddress);
        result.add(4, param -> param.getCreatedAt().toString());
        result.add(5, param -> param.getUpdatedAt().toString());

        return result;
    }

    public static List<Callback<Department, String>> getDepartmentColumnsCallBacks() {
        List<Callback<Department, String>> result = new ArrayList<>();
        result.add(0, param -> param.getId().toString());
        result.add(1, Department::getCode);
        result.add(2, Department::getName);
        result.add(3, param -> param.getNumberOfSeats().toString());
        result.add(4, param -> param.getCreatedAt().toString());
        result.add(5, param -> param.getUpdatedAt().toString());

        return result;
    }

    public static List<Callback<Option, String>> getOptionColumnsCallBacks() {
        List<Callback<Option, String>> result = new ArrayList<>();
        result.add(0, param -> param.getId().toString());
        result.add(1, param -> param.getCandidate().getId().toString());
        result.add(2, param -> param.getCandidate().getName());
        result.add(3, param -> param.getDepartment().getId().toString());
        result.add(4, param -> param.getDepartment().getName());
        result.add(5, param -> param.getCreatedAt().toString());
        result.add(6, param -> param.getUpdatedAt().toString());

        return result;
    }
}
