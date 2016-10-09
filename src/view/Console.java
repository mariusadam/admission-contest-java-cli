package view;

import controller.CandidateController;
import controller.DepartmentController;
import dnl.utils.text.table.TextTable;
import domain.Candidate;
import domain.Entity;
import exception.CandidateException;
import util.UbbArray;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The class responsible for the interaction with the end user
 */
public class Console {
    private CandidateController candidateController;
    private DepartmentController departmentController;
    private Scanner in;
    private PrintStream out;

    /**
     *
     * @param candidateController  {@link CandidateController}
     * @param departmentController {@link DepartmentController}
     */
    public Console(CandidateController candidateController, DepartmentController departmentController) {
        this.candidateController = candidateController;
        this.departmentController = departmentController;
        this.in = new Scanner(System.in);
        this.out = System.out;
    }

    /**
     *
     * @return A string representing the main menu
     */
    private String getMenu() {
        return "Main menu: \n" +
                "1 - Add a new candidate\n" +
                "2 - Update a candidate\n" +
                "3 - Delete a candidate\n" +
                "4 - Show all candidates\n" +
                "5 - Add a new department\n" +
                "6 - Update a department\n" +
                "7 - Delete a department\n" +
                "8 - Show all department\n"
                ;
    }

    private void addCandidate() {
        String name;
        String phone;
        String address;

        this.out.print("Enter the name of the candidate: ");
        name = this.in.nextLine();
        this.out.print("Enter the phone of the candidate: ");
        phone = this.in.nextLine();
        this.out.print("Enter the address of the candidate: ");
        address = this.in.nextLine();

        try {
            Candidate cand = this.candidateController.create(name, phone, address);
            this.out.println("Added " + cand);
        } catch (CandidateException ex) {
            this.out.println(ex.getMessage());
        }
    }

    private void updateCandidate() {
        Integer id;
        String name;
        String phone;
        String address;

        this.out.print("Enter the id of the candidate you wish to modify: ");
        id = this.in.nextInt();
        this.out.print("Enter the new name of the candidate or leave id blank to not change it: ");
        name = this.in.nextLine();
        this.out.print("Enter the new phone of the candidate or leave id blank to not change it: ");
        phone = this.in.nextLine();
        this.out.print("Enter the new addressof the candidate or leave id blank to not change it: ");
        address = this.in.nextLine();

        try {
            Candidate cand = this.candidateController.update(id, name, phone, address);
            this.out.println("Updated " + cand);
        } catch (CandidateException ex) {
            this.out.println(ex.getMessage());
        }
    }

    private void printCandidates()
    {
        String[] columns = {"id" ,"name", "phone", "address"};
        UbbArray<Entity> candidates = this.candidateController.getAll();
        Object[][] data = new Object[candidates.getSize()][];

        for (int i = 0; i < candidates.getSize(); i++) {
            Candidate candidate = (Candidate) candidates.getAt(i);
            Object[] row = new Object[4];
            row[0] = candidate.getId();
            row[1] = candidate.getName();
            row[2] = candidate.getPhone();
            row[3] = candidate.getAddress();
            data[i] = row;
        }

        TextTable table = new TextTable(columns, data);
        table.printTable(this.out, 5);
    }

    private void dispatch(String command) {
        switch (command) {
            case "1":
                this.addCandidate();
                break;
            case "2":
                this.updateCandidate();
                break;
            case "4":
                this.printCandidates();
                break;
            default:
                throw new InputMismatchException("You entered an invalid command");
        }
    }

    public void run() {
        String command;
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                this.out.println(this.getMenu());
                command = this.in.nextLine();
                this.dispatch(command);
            } catch (InputMismatchException ex) {
                this.out.println(ex.getMessage());
            }
        }
    }
}
