package com.ubb.map.view.gui.department;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ubb.map.domain.Department;
import com.ubb.map.services.DepartmentCrudService;
import com.ubb.map.services.filters.types.PropertyFilter;
import com.ubb.map.services.filters.types.multiple.BetweenFilter;
import com.ubb.map.services.filters.types.multiple.NotBetweenFilter;
import com.ubb.map.services.filters.types.simple.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DepartmentController implements Initializable {

    @Inject private DepartmentCrudService departmentCrudService;

    @FXML private Button searchButton;
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
    @FXML private HBox noOfSeatsFilterHbox;
    @FXML private HBox idFilterHbox;
    @FXML private HBox createdAtFilterHbox;
    @FXML private HBox updatedAtFilterHbox;
    @FXML private DatePicker createdAtMatchDatePicker;
    @FXML private DatePicker createdAtMinDatePicker;
    @FXML private DatePicker createdAtMaxDatePicker;
    @FXML private DatePicker updatedAtMatchDatePicker;
    @FXML private DatePicker updatedAtMinDatePicker;
    @FXML private DatePicker updatedAtMaxDatePicker;
    @FXML private TableView<Department> departmentsTableView;

    @FXML private TableColumn<Department, Integer> idColumn;
    @FXML private TableColumn<Department, String>  codeColumn;
    @FXML private TableColumn<Department, String>  nameColumn;
    @FXML private TableColumn<Department, Integer> noOfSeatsColumn;
    @FXML private TableColumn<Department, Date>    createdAtColumn;
    @FXML private TableColumn<Department, Date>    updatedAtColumn;
    private ObservableList<Department> departmentsObservableList;
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ValueProvider idProvider = () -> idMatchTextField.getText();
        ValueProvider firstIdProvider = () -> idMinTextField.getText();
        ValueProvider secondIdProvider = () -> idMatchTextField.getText();
        idFilterComboBox.getItems().addAll(
                new EqualsFilter("id", idProvider),
                new NotEqualsFilter("id", idProvider),
                new BetweenFilter("id", firstIdProvider, secondIdProvider),
                new NotBetweenFilter("id", firstIdProvider, secondIdProvider)
        );

        ValueProvider codeProvider = () -> codeMatchTextField.getText();
        codeFilterComboBox.getItems().addAll(
                new EqualsFilter("code", codeProvider),
                new NotEqualsFilter("code", codeProvider),
                new ContainsFilter("code", codeProvider),
                new NotContainsFilter("code", codeProvider)
        );

        ValueProvider nameProvider = () -> nameMatchTextField.getText();
        nameFilterComboBox.getItems().addAll(
                new EqualsFilter("name", nameProvider),
                new NotEqualsFilter("name", nameProvider),
                new ContainsFilter("name", nameProvider),
                new NotContainsFilter("name", nameProvider)
        );

        ValueProvider noSeatsProvider = () -> noOfSeatsMatchTextField.getText();
        ValueProvider noSeatsFirstProvider = () -> noOfSeatsMinTextField.getText();
        ValueProvider noSeatsSecondProvider = () -> noOfSeatsMaxTextField.getText();
        noOfSeatsFilterComboBox.getItems().addAll(
                new EqualsFilter("number_of_seats", noSeatsProvider),
                new NotEqualsFilter("number_of_seats", noSeatsProvider),
                new BetweenFilter("number_of_seats", noSeatsFirstProvider, noSeatsSecondProvider),
                new NotBetweenFilter("number_of_seats", noSeatsFirstProvider, noSeatsSecondProvider)
        );

        ValueProvider createdAtProvider = () -> createdAtMatchDatePicker.getValue();
        ValueProvider createdAtFirstProvider = () -> createdAtMinDatePicker.getValue();
        ValueProvider createdAtSecondProvider = () -> createdAtMaxDatePicker.getValue();
        createdAtFilterComboBox.getItems().addAll(
                new EqualsFilter("created_at", createdAtProvider),
                new NotEqualsFilter("created_at", createdAtProvider),
                new BetweenFilter("created_at", createdAtFirstProvider, createdAtSecondProvider),
                new NotBetweenFilter("created_at", createdAtFirstProvider, createdAtSecondProvider)
        );

        ValueProvider updatedAtProvider = () -> updatedAtMatchDatePicker.getValue();
        ValueProvider updatedAtFirstProvider = () -> updatedAtMinDatePicker.getValue();
        ValueProvider updatedAtSecondProvider = () -> updatedAtMaxDatePicker.getValue();
        updatedAtFilterComboBox.getItems().addAll(
                new EqualsFilter("updated_at", updatedAtProvider),
                new NotEqualsFilter("updated_at", updatedAtProvider),
                new BetweenFilter("updated_at", updatedAtFirstProvider, updatedAtSecondProvider),
                new NotBetweenFilter("updated_at", updatedAtFirstProvider, updatedAtSecondProvider)
        );

        idFilterHbox.setVisible(false);
        noOfSeatsFilterHbox.setVisible(false);
        createdAtFilterHbox.setVisible(false);
        updatedAtFilterHbox.setVisible(false);
        idMatchTextField.setVisible(true);
        noOfSeatsMatchTextField.setVisible(true);
        createdAtMatchDatePicker.setVisible(true);
        updatedAtMatchDatePicker.setVisible(true);

//        idColumn = new TableColumn<>("#Id");
        idColumn.setText("#Id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        codeColumn.setText("Code");
        codeColumn.setMinWidth(100);
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        nameColumn.setText("Name");
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        noOfSeatsColumn.setText("No.Seats");
        noOfSeatsColumn.setMinWidth(30);
        noOfSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfSeats"));

        createdAtColumn.setText("Created at");
        createdAtColumn.setMinWidth(150);
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        updatedAtColumn.setText("Updated at");
        updatedAtColumn.setMinWidth(150);
        updatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));

        this.departmentsObservableList = FXCollections.observableArrayList();
        departmentsObservableList.addAll(departmentCrudService.getAll());
        departmentsTableView.setItems(departmentsObservableList);
        departmentsTableView
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    this.loadDepartments(newValue);
                });
    }

    private void loadDepartments(Department department) {
        codeTextField.setText(department.getCode());
        nameTextField.setText(department.getName());
        noOfSeatsTextField.setText(department.getNumberOfSeats().toString());
    }

    private void ensureCorrectFieldsAreVisible(PropertyFilter filter, HBox hBoxContainer, Control textField) {
        if (filter.isSingleValued()) {
            hBoxContainer.setVisible(false);
            textField.setVisible(true);
        } else {
            textField.setVisible(false);
            hBoxContainer.setVisible(true);
        }
    }

    @FXML
    void onIdFilterComboBox(ActionEvent event) {
        ensureCorrectFieldsAreVisible(idFilterComboBox.getValue(), idFilterHbox, idMatchTextField);

    }

    @FXML
    void onNoOfSeatsFilterComboBox(ActionEvent event) {
        ensureCorrectFieldsAreVisible(noOfSeatsFilterComboBox.getValue(), noOfSeatsFilterHbox, noOfSeatsMatchTextField);
    }

    @FXML
    void onCreatedAtFilterComboBox(ActionEvent event) {
        ensureCorrectFieldsAreVisible(createdAtFilterComboBox.getValue(), createdAtFilterHbox, createdAtMatchDatePicker);
    }

    @FXML
    void onUpdateAtFilterComboBox(ActionEvent event) {
        ensureCorrectFieldsAreVisible(updatedAtFilterComboBox.getValue(), updatedAtFilterHbox, updatedAtMatchDatePicker);
    }

    @FXML
    void onSearchButton_clicked(ActionEvent event) {
        this.departmentsObservableList.clear();
        this.departmentsObservableList.addAll(this.departmentCrudService.getFiltered(getFilters()));
    }

    Collection<PropertyFilter> getFilters() {
        return Stream.of(
                idFilterComboBox.getSelectionModel().getSelectedItem(),
                codeFilterComboBox.getSelectionModel().getSelectedItem(),
                nameFilterComboBox.getSelectionModel().getSelectedItem(),
                noOfSeatsFilterComboBox.getSelectionModel().getSelectedItem(),
                createdAtFilterComboBox.getSelectionModel().getSelectedItem(),
                updatedAtFilterComboBox.getSelectionModel().getSelectedItem()
        ).filter(propertyFilter -> propertyFilter != null).collect(Collectors.toList());
    }
}
