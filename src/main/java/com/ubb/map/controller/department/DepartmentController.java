package com.ubb.map.controller.department;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ubb.map.controller.BaseController;
import com.ubb.map.domain.Department;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.InvalidObjectException;
import com.ubb.map.exception.RepositoryException;
import com.ubb.map.services.crud.BaseCrudService;
import com.ubb.map.services.crud.DepartmentCrudService;
import com.ubb.map.services.filters.NullFilter;
import com.ubb.map.services.filters.PropertyFilter;
import com.ubb.map.services.filters.ValueProvider;
import com.ubb.map.services.filters.multiple.BetweenFilter;
import com.ubb.map.services.filters.multiple.NotBetweenFilter;
import com.ubb.map.services.filters.simple.ContainsFilter;
import com.ubb.map.services.filters.simple.EqualsFilter;
import com.ubb.map.services.filters.simple.NotContainsFilter;
import com.ubb.map.services.filters.simple.NotEqualsFilter;
import com.ubb.map.controller.AlertBox;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.converter.IntegerStringConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DepartmentController extends BaseController<Integer, Department> {
    @FXML private TextField idTextField;
    @FXML private TextField codeTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField noOfSeatsTextField;
    @FXML private TextField codeMatchTextField;
    @FXML private TextField noOfSeatsMinTextField;
    @FXML private TextField noOfSeatsMaxTextField;
    @FXML private TextField idMinTextField;
    @FXML private TextField idMaxTextField;
    @FXML private TextField idMatchTextField;
    @FXML private TextField nameMatchTextField;
    @FXML private TextField noOfSeatsMatchTextField;
    @FXML private ComboBox<PropertyFilter> codeFilterComboBox;
    @FXML private ComboBox<PropertyFilter> createdAtFilterComboBox;
    @FXML private ComboBox<PropertyFilter> updatedAtFilterComboBox;
    @FXML private ComboBox<PropertyFilter> noOfSeatsFilterComboBox;
    @FXML private ComboBox<PropertyFilter> nameFilterComboBox;
    @FXML private ComboBox<PropertyFilter> idFilterComboBox;
    @FXML private HBox noOfSeatsFilterHBox;
    @FXML private HBox idFilterHBox;
    @FXML private HBox createdAtFilterHBox;
    @FXML private HBox updatedAtFilterHBox;
    @FXML private DatePicker createdAtMatchDatePicker;
    @FXML private DatePicker createdAtMinDatePicker;
    @FXML private DatePicker createdAtMaxDatePicker;
    @FXML private DatePicker updatedAtMatchDatePicker;
    @FXML private DatePicker updatedAtMinDatePicker;
    @FXML private DatePicker updatedAtMaxDatePicker;

    @Inject private DepartmentCrudService mainCrudService;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ValueProvider idProvider = () -> idMatchTextField.getText();
        ValueProvider firstIdProvider = () -> idMinTextField.getText();
        ValueProvider secondIdProvider = () -> idMaxTextField.getText();
        PropertyFilter nullFilter = new NullFilter();
        idFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter("id", idProvider),
                new NotEqualsFilter("id", idProvider),
                new BetweenFilter("id", firstIdProvider, secondIdProvider),
                new NotBetweenFilter("id", firstIdProvider, secondIdProvider)
        );

        ValueProvider codeProvider = () -> codeMatchTextField.getText();
        codeFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter("code", codeProvider),
                new NotEqualsFilter("code", codeProvider),
                new ContainsFilter("code", codeProvider),
                new NotContainsFilter("code", codeProvider)
        );

        ValueProvider nameProvider = () -> nameMatchTextField.getText();
        nameFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter("name", nameProvider),
                new NotEqualsFilter("name", nameProvider),
                new ContainsFilter("name", nameProvider),
                new NotContainsFilter("name", nameProvider)
        );

        ValueProvider noSeatsProvider = () -> noOfSeatsMatchTextField.getText();
        ValueProvider noSeatsFirstProvider = () -> noOfSeatsMinTextField.getText();
        ValueProvider noSeatsSecondProvider = () -> noOfSeatsMaxTextField.getText();
        noOfSeatsFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter("number_of_seats", noSeatsProvider),
                new NotEqualsFilter("number_of_seats", noSeatsProvider),
                new BetweenFilter("number_of_seats", noSeatsFirstProvider, noSeatsSecondProvider),
                new NotBetweenFilter("number_of_seats", noSeatsFirstProvider, noSeatsSecondProvider)
        );

        ValueProvider createdAtProvider = () -> createdAtMatchDatePicker.getValue();

        ValueProvider createdAtFirstProvider = () -> createdAtMinDatePicker.getValue();
        ValueProvider createdAtSecondProvider = () -> createdAtMaxDatePicker.getValue();
        createdAtFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter("created_at", createdAtProvider),
                new NotEqualsFilter("created_at", createdAtProvider),
                new BetweenFilter("created_at", createdAtFirstProvider, createdAtSecondProvider),
                new NotBetweenFilter("created_at", createdAtFirstProvider, createdAtSecondProvider)
        );

        ValueProvider updatedAtProvider = () -> updatedAtMatchDatePicker.getValue();
        ValueProvider updatedAtFirstProvider = () -> updatedAtMinDatePicker.getValue();
        ValueProvider updatedAtSecondProvider = () -> updatedAtMaxDatePicker.getValue();
        updatedAtFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter("updated_at", updatedAtProvider),
                new NotEqualsFilter("updated_at", updatedAtProvider),
                new BetweenFilter("updated_at", updatedAtFirstProvider, updatedAtSecondProvider),
                new NotBetweenFilter("updated_at", updatedAtFirstProvider, updatedAtSecondProvider)
        );

        idTextField.setDisable(true);
        idFilterHBox.setVisible(false);
        noOfSeatsFilterHBox.setVisible(false);
        createdAtFilterHBox.setVisible(false);
        updatedAtFilterHBox.setVisible(false);
        idMatchTextField.setVisible(true);
        noOfSeatsMatchTextField.setVisible(true);
        createdAtMatchDatePicker.setVisible(true);
        updatedAtMatchDatePicker.setVisible(true);

        super.initialize(location, resources);

        //TODO export data functionality
    }

    @Override
    protected BaseCrudService<Integer, Department> getMainCrudService() {
        return mainCrudService;
    }

    protected void initializeMainTableView() {
        mainTableView = new TableView<>();

        TableColumn<Department, Integer> idColumn = new TableColumn<>();
        idColumn.setText("#Id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Department, String> codeColumn = new TableColumn<>();
        codeColumn.setText("Code");
        codeColumn.setMinWidth(100);
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        codeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        codeColumn.setOnEditCommit(t -> {
            try {
                int index = t.getTablePosition().getRow();
                Department d = t.getTableView().getItems().get(index);
                d.setCode(t.getNewValue());
                d = mainCrudService.update(d);
                t.getTableView().getItems().set(index, d);
            } catch (SQLException | RepositoryException | InvalidObjectException e) {
                AlertBox.error(e.getMessage());
            }
        });

        TableColumn<Department, String> nameColumn = new TableColumn<>();
        nameColumn.setText("Name");
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(t -> {
            try {
                int index = t.getTablePosition().getRow();
                Department d = t.getTableView().getItems().get(index);
                d.setName(t.getNewValue());
                d = mainCrudService.update(d);
                t.getTableView().getItems().set(index, d);
            } catch (SQLException | RepositoryException | InvalidObjectException e) {
                AlertBox.error(e.getMessage());
            }
        });

        TableColumn<Department, Integer> noOfSeatsColumn = new TableColumn<>();
        noOfSeatsColumn.setText("No.Seats");
        noOfSeatsColumn.setMinWidth(30);
        noOfSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfSeats"));
        noOfSeatsColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        noOfSeatsColumn.setOnEditCommit(t -> {
            try {
                int index = t.getTablePosition().getRow();
                Department d = t.getTableView().getItems().get(index);
                d.setNumberOfSeats(t.getNewValue());
                d = mainCrudService.update(d);
                t.getTableView().getItems().set(index, d);
            } catch (SQLException | RepositoryException | InvalidObjectException e) {
                AlertBox.error(e.getMessage());
            }
        });

        TableColumn<Department, Date> createdAtColumn = new TableColumn<>();
        createdAtColumn.setText("Created at");
        createdAtColumn.setMinWidth(150);
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<Department, Date> updatedAtColumn = new TableColumn<>();
        updatedAtColumn.setText("Updated at");
        updatedAtColumn.setMinWidth(150);
        updatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        this.entityObservableList = FXCollections.observableArrayList();
        mainTableView.setItems(entityObservableList);
        mainTableView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> this.loadDepartment(newValue));
        //noinspection unchecked
        mainTableView.getColumns().addAll(
                idColumn,
                codeColumn,
                nameColumn,
                noOfSeatsColumn,
                createdAtColumn,
                updatedAtColumn
        );
    }

    private void loadDepartment(Department department) {
        if (department == null) {
            return;
        }
        idTextField.setText(department.getId().toString());
        codeTextField.setText(department.getCode());
        nameTextField.setText(department.getName());
        noOfSeatsTextField.setText(department.getNumberOfSeats().toString());
    }

    private void clearDetails() {
        idTextField.setText("");
        codeTextField.setText("");
        nameTextField.setText("");
        noOfSeatsTextField.setText("");
    }

    @FXML
    void onIdFilterComboBox(ActionEvent event) {
        ensureCorrectFieldsAreVisible(idFilterComboBox.getValue(), idFilterHBox, idMatchTextField);

    }

    @FXML
    void onNoOfSeatsFilterComboBox(ActionEvent event) {
        ensureCorrectFieldsAreVisible(noOfSeatsFilterComboBox.getValue(), noOfSeatsFilterHBox, noOfSeatsMatchTextField);
    }

    @FXML
    void onCreatedAtFilterComboBox(ActionEvent event) {
        ensureCorrectFieldsAreVisible(createdAtFilterComboBox.getValue(), createdAtFilterHBox, createdAtMatchDatePicker);
    }

    @FXML
    void onUpdateAtFilterComboBox(ActionEvent event) {
        ensureCorrectFieldsAreVisible(updatedAtFilterComboBox.getValue(), updatedAtFilterHBox, updatedAtMatchDatePicker);
    }

    @FXML
    private void onAddButton_clicked(ActionEvent event) {
        try {
            mainCrudService.create(codeTextField.getText(), nameTextField.getText(), noOfSeatsTextField.getText());
            clearDetails();
            reloadMainTable();
        } catch (InvalidObjectException | DuplicateEntryException | SQLException ex) {
            AlertBox.error(ex.getMessage());
        }
    }

    @FXML
    private void onUpdateButton_clicked(ActionEvent event) {
        try {
            mainCrudService.update(idTextField.getText(), codeTextField.getText(), nameTextField.getText(), noOfSeatsTextField.getText());
            reloadMainTable();
        } catch (Exception ex) {
            AlertBox.error(ex.getMessage());
        }
    }

    @FXML
    private void onDeleteButton_clicked(ActionEvent event) {
        try {
            for (Department dep : mainTableView.getSelectionModel().getSelectedItems()) {
                mainCrudService.delete(dep.getId().toString());
            }
            clearDetails();
            reloadMainTable();
        } catch (Exception ex) {
            AlertBox.error(ex.getMessage());
        }
    }

    @FXML
    private void onResetFiltersButton_clicked(ActionEvent event) {
        idFilterComboBox.getSelectionModel().clearSelection();
        codeFilterComboBox.getSelectionModel().clearSelection();
        nameFilterComboBox.getSelectionModel().clearSelection();
        noOfSeatsFilterComboBox.getSelectionModel().clearSelection();
        createdAtFilterComboBox.getSelectionModel().clearSelection();
        updatedAtFilterComboBox.getSelectionModel().clearSelection();

        idMatchTextField.setText("");
        idMinTextField.setText("");
        idMaxTextField.setText("");
        codeMatchTextField.setText("");
        noOfSeatsMatchTextField.setText("");
        noOfSeatsMinTextField.setText("");
        noOfSeatsMaxTextField.setText("");
        createdAtMatchDatePicker.setValue(null);
        createdAtMinDatePicker.setValue(null);
        createdAtMaxDatePicker.setValue(null);
        updatedAtMatchDatePicker.setValue(null);
        updatedAtMinDatePicker.setValue(null);
        updatedAtMaxDatePicker.setValue(null);
    }

    protected List<PropertyFilter> getFilters() {
        return Stream.of(
                idFilterComboBox.getSelectionModel().getSelectedItem(),
                codeFilterComboBox.getSelectionModel().getSelectedItem(),
                nameFilterComboBox.getSelectionModel().getSelectedItem(),
                noOfSeatsFilterComboBox.getSelectionModel().getSelectedItem(),
                createdAtFilterComboBox.getSelectionModel().getSelectedItem(),
                updatedAtFilterComboBox.getSelectionModel().getSelectedItem()
        ).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
