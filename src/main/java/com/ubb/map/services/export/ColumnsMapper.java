package com.ubb.map.services.export;

import com.ubb.map.domain.*;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by marius on 1/20/2017.
 */
//TODO change this class's methods to return an array of callback instead to map values manually
public class ColumnsMapper {
    public static final String ID_FIELD = "id";

    private static Map<String, Object> getEntityMap(Entity obj) {
        Map<String, Object> result = new HashMap<>();
        result.put(ID_FIELD, obj.getId());
        result.put("created_at", obj.getCreatedAt().toString());
        result.put("updated_at", obj.getUpdatedAt().toString());

        return result;
    }

    public static <T> Map<String, Object> getObjectMap(T obj) {
        if (Objects.equals(obj.getClass().getName(), Candidate.class.getName())) {
            return getCandidateMap((Candidate) obj);
        } else if (Objects.equals(obj.getClass().getName(), Department.class.getName())) {
            return getDepartmentMap((Department) obj);
        } else if (Objects.equals(obj.getClass().getName(), Option.class.getName())) {
            return getOptionMap((Option) obj);
        }

        return new HashMap<>();
    }

    private static Map<String, Object> getDepartmentMap(Department obj) {
        Map<String, Object> result = getEntityMap(obj);
        result.put("name", obj.getName());
        result.put("code", obj.getCode());
        result.put("number_of_seats", obj.getNumberOfSeats());
        return result;
    }

    private static Map<String, Object> getOptionMap(Option obj) {
        Map<String, Object> result = getEntityMap(obj);
        result.put("candidate_id", obj.getCandidate().getId());
        result.put("candidate_name", obj.getCandidate().getName());
        result.put("department_id", obj.getDepartment().getId());
        result.put("department_name", obj.getDepartment().getName());
        return result;
    }

    private static Map<String, Object> getCandidateMap(Candidate obj) {
        Map<String, Object> result = getEntityMap(obj);
        result.put("name", obj.getName());
        result.put("phone", obj.getPhone());
        result.put("address", obj.getAddress());
        return result;
    }
}
