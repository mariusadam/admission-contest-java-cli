package com.ubb.map.controller.candidate;

import com.ubb.map.controller.BaseController;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.InvalidObjectException;
import com.ubb.map.exception.RepositoryException;
import com.ubb.map.services.crud.BaseCrudService;
import com.ubb.map.services.crud.CandidateCrudService;
import com.ubb.map.domain.Candidate;
import com.ubb.map.services.filters.NullFilter;
import com.ubb.map.services.filters.PropertyFilter;
import com.ubb.map.services.filters.ValueProvider;
import com.ubb.map.services.filters.multiple.BetweenFilter;
import com.ubb.map.services.filters.multiple.NotBetweenFilter;
import com.ubb.map.services.filters.simple.ContainsFilter;
import com.ubb.map.services.filters.simple.EqualsFilter;
import com.ubb.map.services.filters.simple.NotContainsFilter;
import com.ubb.map.services.filters.simple.NotEqualsFilter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import com.ubb.map.controller.AlertBox;

import javax.inject.Inject;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by marius on 11/20/16.
 */
public class CandidateController extends BaseController<Integer, Candidate> {
    @FXML private TextField idTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField idMatchTextField;
    @FXML private TextField phoneMatchTextField;
    @FXML private TextField phoneTextField;
    @FXML private TextField idMaxTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField idMinTextField;
    @FXML private TextField addressMatchTextField;
    @FXML private TextField nameMatchTextField;
    @FXML private DatePicker createdAtMinDatePicker;
    @FXML private DatePicker updatedAtMinDatePicker;
    @FXML private DatePicker createdAtMaxDatePicker;
    @FXML private DatePicker updatedAtMaxDatePicker;
    @FXML private DatePicker createdAtMatchDatePicker;
    @FXML private DatePicker updatedAtMatchDatePicker;
    @FXML private HBox idFilterHBox;
    @FXML private HBox updatedAtFilterHBox;
    @FXML private HBox createdAtFilterHBox;
    @FXML private ComboBox<PropertyFilter> idFilterComboBox;
    @FXML private ComboBox<PropertyFilter> nameFilterComboBox;
    @FXML private ComboBox<PropertyFilter> phoneFilterComboBox;
    @FXML private ComboBox<PropertyFilter> createdAtFilterComboBox;
    @FXML private ComboBox<PropertyFilter> addressFilterComboBox;
    @FXML private ComboBox<PropertyFilter> updatedAtFilterComboBox;

    @Inject private CandidateCrudService candidateCrudService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idTextField.setDisable(true);
        PropertyFilter nullFilter = new NullFilter();

        ValueProvider idProvider = () -> idMatchTextField.getText();
        ValueProvider firstIdProvider = () -> idMinTextField.getText();
        ValueProvider secondIdProvider = () -> idMaxTextField.getText();
        idFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter("id", idProvider),
                new NotEqualsFilter("id", idProvider),
                new BetweenFilter("id", firstIdProvider, secondIdProvider),
                new NotBetweenFilter("id", firstIdProvider, secondIdProvider)
        );

        ValueProvider nameProvider = () -> nameMatchTextField.getText();
        nameFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter("name", nameProvider),
                new NotEqualsFilter("name", nameProvider),
                new ContainsFilter("name", nameProvider),
                new NotContainsFilter("name", nameProvider)
        );

        ValueProvider phoneProvider = () -> phoneMatchTextField.getText();
        phoneFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter("phone", phoneProvider),
                new NotEqualsFilter("phone", phoneProvider),
                new ContainsFilter("phone", phoneProvider),
                new NotContainsFilter("phone", phoneProvider)
        );

        ValueProvider addressProvider = () -> addressMatchTextField.getText();
        addressFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter("address", addressProvider),
                new NotEqualsFilter("address", addressProvider),
                new ContainsFilter("address", addressProvider),
                new NotContainsFilter("address", addressProvider)
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
        createdAtFilterHBox.setVisible(false);
        updatedAtFilterHBox.setVisible(false);
        idMatchTextField.setVisible(true);
        createdAtMatchDatePicker.setVisible(true);
        updatedAtMatchDatePicker.setVisible(true);

        super.initialize(location, resources);
        //TODO export data functionality
    }

    @Override
    protected void initializeMainTableView() {
        TableColumn<Candidate, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Candidate, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(t -> {
            try {
                int index = t.getTablePosition().getRow();
                Candidate c = t.getTableView().getItems().get(index);
                c.setName(t.getNewValue());
                c = candidateCrudService.update(c);
                t.getTableView().getItems().set(index, c);
            } catch (SQLException | RepositoryException | InvalidObjectException e) {
                AlertBox.error(e.getMessage());
            }
        });

        TableColumn<Candidate, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setMinWidth(200);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneColumn.setOnEditCommit(t -> {
            try {
                int index = t.getTablePosition().getRow();
                Candidate c = t.getTableView().getItems().get(index);
                c.setPhone(t.getNewValue());
                c = candidateCrudService.update(c);
                t.getTableView().getItems().set(index, c);
            } catch (SQLException | RepositoryException | InvalidObjectException e) {
                AlertBox.error(e.getMessage());
            }
        });

        TableColumn<Candidate, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setMinWidth(200);
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setOnEditCommit(t -> {
            try {
                int index = t.getTablePosition().getRow();
                Candidate c = t.getTableView().getItems().get(index);
                c.setAddress(t.getNewValue());
                c = candidateCrudService.update(c);
                t.getTableView().getItems().set(index, c);
            } catch (SQLException | RepositoryException | InvalidObjectException e) {
                AlertBox.error(e.getMessage());
            }
        });

        TableColumn<Candidate, Date> createdAtColumn = new TableColumn<>();
        createdAtColumn.setText("Created at");
        createdAtColumn.setMinWidth(150);
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<Candidate, Date> updatedAtColumn = new TableColumn<>();
        updatedAtColumn.setText("Updated at");
        updatedAtColumn.setMinWidth(150);
        updatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));


        entityObservableList = FXCollections.observableArrayList();
        mainTableView = new TableView<>();
        mainTableView.setItems(entityObservableList);
        //noinspection unchecked
        mainTableView.getColumns().addAll(
                idColumn,
                nameColumn,
                phoneColumn,
                addressColumn,
                createdAtColumn,
                updatedAtColumn
        );
        mainTableView.
                getSelectionModel().
                selectedItemProperty().
                addListener((observable, oldValue, newValue) -> loadCandidate(newValue));
    }

    @FXML
    void onIdFilterComboBox(ActionEvent event) {
        ensureCorrectFieldsAreVisible(idFilterComboBox.getValue(), idFilterHBox, idMatchTextField);
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
    void onResetFiltersButton_clicked(ActionEvent event) {
        Stream.of(
                idFilterComboBox,
                phoneFilterComboBox,
                nameFilterComboBox,
                addressFilterComboBox
        ).forEach(propertyFilterComboBox -> propertyFilterComboBox.getSelectionModel().clearSelection());
        Stream.of(
                idMatchTextField,
                idMinTextField,
                idMaxTextField,
                phoneMatchTextField,
                nameMatchTextField,
                addressMatchTextField

        ).forEach(textField -> textField.setText(""));
        Stream.of(
                createdAtMatchDatePicker,
                createdAtMinDatePicker,
                createdAtMaxDatePicker,
                updatedAtMatchDatePicker,
                updatedAtMinDatePicker,
                updatedAtMaxDatePicker
        ).forEach(datePicker -> datePicker.setValue(null));
    }

    @FXML
    void onAddButton_clicked(ActionEvent event) {
        try {
            candidateCrudService.create(
                    nameTextField.getText(),
                    phoneTextField.getText(),
                    addressTextField.getText()
            );
            reloadMainTable();
        } catch (InvalidObjectException | DuplicateEntryException | SQLException ex) {
            AlertBox.error(ex.getMessage());
        }
    }

    @FXML
    void onUpdateButton_clicked(ActionEvent event) {
        try {
            candidateCrudService.update(
                    idTextField.getText(),
                    nameTextField.getText(),
                    phoneTextField.getText(),
                    addressTextField.getText()
            );
            reloadMainTable();
        } catch (InvalidObjectException | RepositoryException | SQLException ex) {
            AlertBox.error(ex.getMessage());
        }
    }

    @FXML
    void onDeleteButton_clicked(ActionEvent event) {
        try {
            List<Candidate> selected = mainTableView.getSelectionModel().getSelectedItems();
            for (Candidate candidate : selected) {
                candidateCrudService.delete(candidate.getId());
            }

            clearDetails();
            reloadMainTable();
        } catch (RepositoryException | SQLException ex) {
            AlertBox.error(ex.getMessage());
        }
    }

    private void loadCandidate(Candidate candidate) {
        if (null != candidate) {
            idTextField.setText(candidate.getId().toString());
            nameTextField.setText(candidate.getName());
            phoneTextField.setText(candidate.getPhone());
            addressTextField.setText(candidate.getAddress());
        }
    }

    private void clearDetails() {
        idTextField.clear();
        phoneTextField.clear();
        nameTextField.clear();
        addressTextField.clear();
    }

    @Override
    protected BaseCrudService<Integer, Candidate> getMainCrudService() {
        return candidateCrudService;
    }

    @Override
    protected List<PropertyFilter> getFilters() {
        return Stream.of(
                idFilterComboBox,
                phoneFilterComboBox,
                nameFilterComboBox,
                addressFilterComboBox,
                createdAtFilterComboBox,
                updatedAtFilterComboBox
        ).map(propertyFilterComboBox -> propertyFilterComboBox.getSelectionModel().getSelectedItem()
        ).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
