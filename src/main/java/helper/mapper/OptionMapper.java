package helper.mapper;

import domain.Candidate;
import domain.Department;
import domain.Option;
import repository.RepositoryInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by marius on 11/20/16.
 */
public class OptionMapper implements MapperInterface<Integer, Option>{
    private RepositoryInterface<Integer, Candidate> candidateRepo;
    private RepositoryInterface<String, Department> depRepo;

    public OptionMapper(RepositoryInterface<Integer, Candidate> candidateRepo, RepositoryInterface<String, Department> depRepo) {
        this.candidateRepo = candidateRepo;
        this.depRepo = depRepo;
    }

    @Override
    public Option createObject(ResultSet row) throws SQLException {
        Option option = new Option();
        option.setId(row.getInt("id"));
        option.setCandidate(this.candidateRepo.findById(row.getInt("candidate_id")));
        option.setDepartment(this.depRepo.findById(row.getString("department_id")));

        return option;
    }

    @Override
    public Map<String, String> toMap(Option object) {
        Map<String, String> properties = new Hashtable<>();
        if (object.getId() != null) {
            properties.put("id", object.getId().toString());
        }
        properties.put("candidate_id", object.getCandidate().getId().toString());
        properties.put("department_id", object.getDepartment().getId());

        return properties;
    }
}
