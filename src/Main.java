import controller.CandidateController;
import controller.DepartmentController;
import domain.Candidate;
import repository.CandidateRepository;
import repository.DepartmentRepository;
import repository.Repository;
import util.UbbArray;
import validator.CandidateValidator;
import validator.DepartmentValidator;
import view.Console;

public class Main {

    public static void main(String[] args) {
        CandidateRepository candidateRepository = new CandidateRepository();
        CandidateValidator candidateValidator = new CandidateValidator();
        CandidateController candidateController = new CandidateController(candidateRepository, candidateValidator);

        DepartmentRepository departmentRepository = new DepartmentRepository();
        DepartmentValidator departmentValidator = new DepartmentValidator();
        DepartmentController departmentController = new DepartmentController(departmentRepository, departmentValidator);

        Console console = new Console(candidateController, departmentController);

        console.run();
//        return;
        Candidate c1 = new Candidate(1, "c1", "111111111", "some address");
        Candidate c2 = new Candidate(2, "c2", "222222222", "some address");
        Candidate c3 = new Candidate(3, "c3", "333333333", "some address");

        UbbArray<Candidate> arr = new UbbArray<Candidate>();
        arr.add(c1);
        arr.add(c2);
        arr.add(c3);

        for (int i = 0; i < arr.getSize(); i++) {
            System.out.println(arr.getAt(i));
        }
        arr.removeAt(arr.find(c1));
        System.out.println("=======================");
        for (int i = 0; i < arr.getSize(); i++) {
            System.out.println(arr.getAt(i));
        }
        arr.removeAt(arr.find(c3));
        System.out.println("=======================");
        for (int i = 0; i < arr.getSize(); i++) {
            System.out.println(arr.getAt(i));
        }
    }
}
