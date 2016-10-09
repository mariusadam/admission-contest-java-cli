package view;

import controller.CandidateController;
import controller.DepartmentController;
import dnl.utils.text.table.TextTable;
import domain.Candidate;
import domain.Department;
import domain.Entity;
import exception.CandidateException;
import exception.DepartmentException;
import util.UbbArray;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
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
     * @return A string representing the main menu
     */
    private String getMenu() {
        return "Main menu: \n" +
                "x - Exit\n" +
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

    private void printItemsAsTable(String[] columns, Object[][] data) {
        TextTable table = new TextTable(columns, data);
        table.printTable(this.out, 30);
        this.out.println();
    }

    private void printCandidates() {
        String[] columns = {"id", "name", "phone", "address"};
        UbbArray<Entity> candidates = this.candidateController.getAll();
        Object[][] data = new Object[candidates.getSize()][];

        for (int i = 0; i < candidates.getSize(); i++) {
            Candidate candidate = (Candidate) candidates.getAt(i);
            Object[] row = {
                    candidate.getId(),
                    candidate.getName(),
                    candidate.getPhone(),
                    candidate.getAddress(),
            };
            data[i] = row;
        }

        this.printItemsAsTable(columns, data);
    }

    private void deleteCandidate() {
        Integer id;
        this.out.print("Enter the id of the candidate you wish to delete: ");
        id = this.in.nextInt();

        try {
            Candidate candidate = this.candidateController.delete(id);
            this.out.println("Deleted: " + candidate);
        } catch (NoSuchElementException ex) {
            this.out.println("Could not find the candidate with id " + id);
        }
    }

    private void addDepartment() {
        String name;
        Integer numberOfSeats;

        this.out.print("Enter the nae of the department: ");
        name = this.in.nextLine();
        this.out.print("Enter the number of seats of the department: ");
        numberOfSeats = this.in.nextInt();

        try {
            Department department = this.departmentController.create(name, numberOfSeats);
            this.out.println("Updated " + department);
        } catch (DepartmentException ex) {
            this.out.println(ex.getMessage());
        }
    }

    private void updateDepartment() {
        Integer id;
        String name;
        Integer numberOfSeats;

        this.out.print("Enter the id of the department you with to modify: ");
        id = this.in.nextInt();
        this.out.print("Enter the new phone of the department or leave id blank to not change it: ");
        name = this.in.nextLine();
        this.out.print("Enter the number of seats of the department or leave id blank to not change it: ");
        numberOfSeats = this.in.nextInt();

        try {
            Department department = this.departmentController.update(id, name, numberOfSeats);
            this.out.println("Updated " + department);
        } catch (DepartmentException ex) {
            this.out.println(ex.getMessage());
        }
    }

    private void deleteDepartment() {
        Integer id;

        this.out.print("Enter the id of the department you with to modify: ");
        id = this.in.nextInt();
        try {
            Department department = this.departmentController.delete(id);
            this.out.println("Deleted: " + department);
        } catch (NoSuchElementException ex) {
            this.out.println("Could not find the department with id " + id);
        }
    }

    private void printDepartments() {
        String[] columns = {"id", "name", "number of seats"};
        UbbArray<Entity> departments = this.departmentController.getAll();
        Object[][] data = new Object[departments.getSize()][];

        for (int i = 0; i < departments.getSize(); i++) {
            Department department = (Department) departments.getAt(i);
            Object[] row = {
                    department.getId(),
                    department.getName(),
                    department.getNumberOfSeats(),
            };
            data[i] = row;
        }

        this.printItemsAsTable(columns, data);
    }

    private void dispatch(String command) {
        switch (command) {
            case "1":
                this.addCandidate();
                break;
            case "2":
                this.updateCandidate();
                break;
            case "3":
                this.deleteCandidate();
                break;
            case "4":
                this.printCandidates();
                break;
            case "5":
                this.addDepartment();
                break;
            case "6":
                this.updateDepartment();
                break;
            case "7":
                this.deleteDepartment();
                break;
            case "8":
                this.printDepartments();
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
                if (command.equals("x")) {
                    this.out.println("Exiting the program now...");
                    break;
                }

                this.dispatch(command);
            } catch (InputMismatchException ex) {
                this.out.println(ex.getMessage());
            }
        }
    }
}
