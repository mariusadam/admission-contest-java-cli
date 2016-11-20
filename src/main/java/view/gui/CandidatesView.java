package view.gui;

import controller.CandidateController;
import domain.Candidate;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.List;

/**
 * Created by marius on 11/20/16.
 */
public class CandidatesView {
    private CandidateController controller;
    private TableView<Candidate> candidateTableView;
    private ObservableList<Candidate> candidateObservableList;
    private VBox layout;

    private TableColumn<Candidate, Integer> idColumn;
    private TableColumn<Candidate, String> nameColumn;
    private TableColumn<Candidate, String> phoneColumn;
    private TableColumn<Candidate, String> addressColumn;

    private TextField idInput;
    private TextField nameInput;
    private TextField phoneInput;
    private TextField addressInput;
    private TextField nameSearchInput;

    private Button addButton;
    private Button deleteButton;
    private Button updateButton;
    private Button clearButton;

    public CandidatesView(CandidateController controller) {
        this.controller = controller;
        this.initComponents();
    }

    public Pane getPane() {
        return this.layout;
    }

    private void initComponents() {
        this.idColumn = new TableColumn<>("Id");
        this.idColumn.setMinWidth(50);
        this.idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        this.nameColumn = new TableColumn<>("Name");
        this.nameColumn.setMinWidth(150);
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        this.phoneColumn = new TableColumn<>("Phone");
        this.phoneColumn.setMinWidth(200);
        this.phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        this.addressColumn = new TableColumn<>("Address");
        this.addressColumn.setMinWidth(200);
        this.addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        this.candidateTableView = new TableView<>();
        //noinspection unchecked
        this.candidateTableView.getColumns().addAll(idColumn, nameColumn, phoneColumn, addressColumn);
        this.candidateTableView.
                getSelectionModel().
                selectedItemProperty().
                addListener((observable, oldValue, newValue) -> this.loadCandidate(newValue));

        this.idInput = new TextField();
        this.idInput.setPromptText("Id");

        this.nameInput = new TextField();
        this.nameInput.setPromptText("Name");

        this.phoneInput = new TextField();
        this.phoneInput.setPromptText("Phone");

        this.addressInput = new TextField();
        this.addressInput.setPromptText("Address");

        this.nameSearchInput = new TextField();
        this.nameSearchInput.setPromptText("Search by name");
        this.nameSearchInput.textProperty().addListener(this::filterList);

        VBox inputLayout = new VBox();
        inputLayout.getChildren().addAll(idInput, nameInput, phoneInput, addressInput, nameSearchInput);
        inputLayout.setPadding(new Insets(10, 10, 10, 10));
        inputLayout.setSpacing(5);

        this.addButton = new Button("Add");
        this.addButton.setMinWidth(100);
        this.addButton.setOnAction(this::addButtonListener);

        this.deleteButton = new Button("Delete");
        this.deleteButton.setMinWidth(100);
        this.deleteButton.setOnMouseClicked(this::deleteButtonOnClick);

        this.updateButton = new Button("Update");
        this.updateButton.setMinWidth(100);
        this.updateButton.setOnMouseClicked(this::updateButtonOnClick);

        this.clearButton = new Button("Clear");
        this.clearButton.setMinWidth(100);
        this.clearButton.setOnMouseClicked(this::clearButtonOnClick);

        HBox buttonsLayout = new HBox();
        buttonsLayout.getChildren().addAll(addButton, deleteButton, updateButton, clearButton);
        buttonsLayout.setPadding(new Insets(10, 10, 10, 10));
        buttonsLayout.setSpacing(5);

        VBox functionalLayout = new VBox();
        functionalLayout.getChildren().addAll(inputLayout, buttonsLayout);
        functionalLayout.setPadding(new Insets(10, 10, 10, 10));

        this.layout = new VBox();
        this.layout.getChildren().addAll(candidateTableView, functionalLayout);

        this.candidateObservableList = FXCollections.observableArrayList();
        this.updateList();
    }

    private void updateList(Collection<Candidate> all) {
        this.candidateObservableList.clear();
        this.candidateObservableList.addAll(all);
        this.candidateTableView.setItems(this.candidateObservableList);
    }

    private void updateList() {
        this.updateList(this.controller.getAll());
    }

    private void clearButtonOnClick(MouseEvent mouseEvent) {
        this.idInput.clear();
        this.nameInput.clear();
        this.phoneInput.clear();
        this.addressInput.clear();
    }

    private void addButtonListener(ActionEvent event) {
        try {
            this.controller.create(
                    nameInput.getText(),
                    phoneInput.getText(),
                    addressInput.getText()
            );
            this.updateList();
        } catch (Exception ex) {
            AlertBox.error(ex.getMessage());
        }
    }

    private void deleteButtonOnClick(MouseEvent mouseEvent) {
        try {
            List<Candidate> selected = this.candidateTableView.getSelectionModel().getSelectedItems();

            for (Candidate candidate : selected) {
                this.controller.delete(candidate.getId());
            }
            this.updateList();
//            this.controller.delete(Integer.parseInt(idInput.getText()));
        } catch (Exception ex) {
            AlertBox.error(ex.getMessage());
        }
    }

    private void updateButtonOnClick(MouseEvent mouseEvent) {
        try {
            this.controller.update(
                    Integer.parseInt(idInput.getText()),
                    nameInput.getText(),
                    phoneInput.getText(),
                    addressInput.getText()
            );
            this.updateList();
        } catch (Exception ex) {
            AlertBox.error(ex.getMessage());
        }
    }

    private void loadCandidate(Candidate candidate) {
        if (null != candidate) {
            idInput.setText(candidate.getId().toString());
            nameInput.setText(candidate.getName());
            phoneInput.setText(candidate.getPhone());
            addressInput.setText(candidate.getAddress());
        }
    }

    private void filterList(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        this.updateList(this.controller.filterByName(newValue));
    }
}
