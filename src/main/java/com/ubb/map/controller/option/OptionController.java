package com.ubb.map.controller.option;

import com.ubb.map.controller.BaseController;
import com.ubb.map.controller.DialogBox;
import com.ubb.map.domain.Department;
import com.ubb.map.domain.Option;
import com.ubb.map.exception.DuplicateEntryException;
import com.ubb.map.exception.InvalidObjectException;
import com.ubb.map.exception.RepositoryException;
import com.ubb.map.services.crud.BaseCrudService;
import com.ubb.map.services.crud.DepartmentCrudService;
import com.ubb.map.services.crud.OptionCrudService;
import com.ubb.map.services.filters.NullFilter;
import com.ubb.map.services.filters.PropertyFilter;
import com.ubb.map.services.filters.ValueProvider;
import com.ubb.map.services.filters.multiple.BetweenFilter;
import com.ubb.map.services.filters.multiple.NotBetweenFilter;
import com.ubb.map.services.filters.simple.EqualsFilter;
import com.ubb.map.services.filters.simple.NotEqualsFilter;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class OptionController extends BaseController<Integer, Option> {
    @FXML
    private TextField candIdMaxTextField;
    @FXML
    private TextField depIdMaxTextField;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField depIdMinTextField;
    @FXML
    private TextField candIdTextField;
    @FXML
    private TextField idMatchTextField;
    @FXML
    private TextField candIdMinTextField;
    @FXML
    private TextField depIdTextField;
    @FXML
    private TextField depIdMatchTextField;
    @FXML
    private TextField candIdMatchTextField;
    @FXML
    private TextField depCodeTextField;
    @FXML
    private TextField idMinTextField;
    @FXML
    private HBox candIdFilterHBox;
    @FXML
    private HBox idFilterHBox;
    @FXML
    private HBox createdAtFilterHBox;
    @FXML
    private HBox depIdFilterHBox;
    @FXML
    private HBox updatedAtFilterHBox;
    @FXML
    private ComboBox<PropertyFilter> depIdFilterComboBox;
    @FXML
    private ComboBox<PropertyFilter> candIdFilterComboBox;
    @FXML
    private ComboBox<PropertyFilter> createdAtFilterComboBox;
    @FXML
    private ComboBox<PropertyFilter> updatedAtFilterComboBox;
    @FXML
    private ComboBox<PropertyFilter> idFilterComboBox;
    @FXML
    private DatePicker createdAtMinDatePicker;
    @FXML
    private DatePicker updatedAtMinDatePicker;
    @FXML
    private DatePicker updatedAtMaxDatePicker;
    @FXML
    private DatePicker createdAtMaxDatePicker;
    @FXML
    private DatePicker createdAtMatchDatePicker;
    @FXML
    private DatePicker updatedAtMatchDatePicker;

    @Inject
    private OptionCrudService optionCrudService;

    @Inject
    private DepartmentCrudService departmentCrudService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idTextField.setEditable(false);
        PropertyFilter nullFilter = new NullFilter();

        ValueProvider idProvider = () -> idMatchTextField.getText();
        ValueProvider minIdProvider = () -> idMinTextField.getText();
        ValueProvider maxIdProvider = () -> idMatchTextField.getText();
        idFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter("id", idProvider),
                new NotEqualsFilter("id", idProvider),
                new BetweenFilter("id", minIdProvider, maxIdProvider),
                new NotBetweenFilter("id", minIdProvider, maxIdProvider)
        );

        ValueProvider candIdProvider = () -> candIdMatchTextField.getText();
        ValueProvider minCandIdProvider = () -> candIdMinTextField.getText();
        ValueProvider maxCandIdProvider = () -> candIdMaxTextField.getText();
        candIdFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter(Option.CANDIDATE_ID_FIELD_NAME, candIdProvider),
                new NotEqualsFilter(Option.CANDIDATE_ID_FIELD_NAME, candIdProvider),
                new BetweenFilter(Option.CANDIDATE_ID_FIELD_NAME, minCandIdProvider, maxCandIdProvider),
                new NotBetweenFilter(Option.CANDIDATE_ID_FIELD_NAME, minCandIdProvider, maxCandIdProvider)
        );

        ValueProvider depIdProvider = () -> depIdMatchTextField.getText();
        ValueProvider mindepIdProvider = () -> depIdMinTextField.getText();
        ValueProvider maxdepIdProvider = () -> depIdMaxTextField.getText();
        depIdFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter(Option.DEPARTMENT_ID_FIELD_NAME, depIdProvider),
                new NotEqualsFilter(Option.DEPARTMENT_ID_FIELD_NAME, depIdProvider),
                new BetweenFilter(Option.DEPARTMENT_ID_FIELD_NAME, mindepIdProvider, maxdepIdProvider),
                new NotBetweenFilter(Option.DEPARTMENT_ID_FIELD_NAME, mindepIdProvider, maxdepIdProvider)
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

        AutoCompletionBinding<String> acb = TextFields.bindAutoCompletion(depCodeTextField, param -> {
            try {
                return departmentCrudService.suggestCodes(param.getUserText());
            } catch (SQLException e) {
                DialogBox.error(e.getMessage());
                return new ArrayList<>();
            }
        });

        acb.setOnAutoCompleted(event -> {
            try {
                Department dep = optionCrudService.getDepartmentRepository().findByCode(event.getCompletion());
                depIdTextField.setText(dep.getId().toString());
            } catch (SQLException | RepositoryException e) {
                DialogBox.error(e.getMessage());
            }
        });

        super.initialize(location, resources);
    }

    @Override
    protected Class<Option> getManagedEntity() {
        return Option.class;
    }

    @Override
    protected void initializeMainTableView() {
        TableColumn<Option, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Option, String> candidateNameColumn = new TableColumn<>("Candidate Name");
        candidateNameColumn.setMinWidth(150);
        candidateNameColumn.setCellValueFactory(param -> Bindings.createStringBinding(
                () -> param.getValue().getCandidate().getName())
        );

        TableColumn<Option, Number> candidateIdColumn = new TableColumn<>("Candidate id");
        candidateIdColumn.setMinWidth(200);
        candidateIdColumn.setCellValueFactory(param -> Bindings.createIntegerBinding(
                () -> param.getValue().getCandidate().getId())
        );

        TableColumn<Option, String> departmentNameColumn = new TableColumn<>("Department Name");
        departmentNameColumn.setMinWidth(150);
        departmentNameColumn.setCellValueFactory(param -> Bindings.createStringBinding(
                () -> param.getValue().getDepartment().getName()
        ));

        TableColumn<Option, Number> departmentIdColumn = new TableColumn<>("Department id");
        departmentIdColumn.setMinWidth(200);
        departmentIdColumn.setCellValueFactory(param -> Bindings.createIntegerBinding(
                () -> param.getValue().getDepartment().getId())
        );

        TableColumn<Option, Date> createdAtColumn = new TableColumn<>();
        createdAtColumn.setText("Created at");
        createdAtColumn.setMinWidth(150);
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<Option, Date> updatedAtColumn = new TableColumn<>();
        updatedAtColumn.setText("Updated at");
        updatedAtColumn.setMinWidth(150);
        updatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));


        entityObservableList = FXCollections.observableArrayList();
        mainTableView = new TableView<>();
        mainTableView.setItems(entityObservableList);
        //noinspection unchecked
        mainTableView.getColumns().addAll(
                idColumn,
                candidateIdColumn,
                candidateNameColumn,
                departmentIdColumn,
                departmentNameColumn,
                createdAtColumn,
                updatedAtColumn
        );
        mainTableView.
                getSelectionModel().
                selectedItemProperty().
                addListener((observable, oldValue, newValue) -> loadOption(newValue));
    }

    @Override
    protected BaseCrudService<Integer, Option> getMainCrudService() {
        return optionCrudService;
    }

    private void loadOption(Option newValue) {
        if (newValue != null) {
            idTextField.setText(newValue.getId().toString());
            candIdTextField.setText(newValue.getCandidate().getId().toString());
            depIdTextField.setText(newValue.getDepartment().getId().toString());
            depCodeTextField.setText(newValue.getDepartment().getCode());
        }
    }

    @Override
    protected List<PropertyFilter> getFilters() {
        return Stream.of(
                idFilterComboBox,
                candIdFilterComboBox,
                depIdFilterComboBox,
                createdAtFilterComboBox,
                updatedAtFilterComboBox
        ).map(propertyFilterComboBox -> propertyFilterComboBox.getSelectionModel().getSelectedItem()
        ).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @FXML
    void onIdFilterComboBox(ActionEvent event) {
        ensureCorrectFieldsAreVisible(idFilterComboBox.getValue(), idFilterHBox, idMatchTextField);
    }

    @FXML
    void onCandIdFilterComboBox(ActionEvent event) {
        ensureCorrectFieldsAreVisible(candIdFilterComboBox.getValue(), candIdFilterHBox, candIdMatchTextField);
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
    void onDepIdFilterComboBox(ActionEvent event) {
        ensureCorrectFieldsAreVisible(depIdFilterComboBox.getValue(), depIdFilterHBox, depIdMatchTextField);
    }

    @FXML
    void onResetFiltersButton_clicked(ActionEvent event) {
        idFilterComboBox.getSelectionModel().clearSelection();
        candIdFilterComboBox.getSelectionModel().clearSelection();
        depIdFilterComboBox.getSelectionModel().clearSelection();
        createdAtFilterComboBox.getSelectionModel().clearSelection();
        updatedAtFilterComboBox.getSelectionModel().clearSelection();

        idMatchTextField.setText("");
        idMinTextField.setText("");
        candIdTextField.setText("");
        depIdTextField.setText("");
        depCodeTextField.setText("");
        createdAtMatchDatePicker.setValue(null);
        createdAtMinDatePicker.setValue(null);
        createdAtMaxDatePicker.setValue(null);
        updatedAtMatchDatePicker.setValue(null);
        updatedAtMinDatePicker.setValue(null);
        updatedAtMaxDatePicker.setValue(null);
    }

    @FXML
    void onAddButton_clicked(ActionEvent event) {
        try {
            optionCrudService.create(
                    candIdTextField.getText(),
                    depIdTextField.getText()
            );
            reloadMainTable();
        } catch (InvalidObjectException | DuplicateEntryException | SQLException | RepositoryException ex) {
            DialogBox.error(ex.getMessage());
        }
    }

    @FXML
    void onUpdateButton_clicked(ActionEvent event) {
        try {
            Option o = optionCrudService.update(
                    idTextField.getText(),
                    candIdTextField.getText(),
                    depIdTextField.getText(),
                    depCodeTextField.getText()
            );
            loadOption(o);
            reloadMainTable();
        } catch (InvalidObjectException | SQLException | RepositoryException ex) {
            DialogBox.error(ex.getMessage());
        }
    }

    @FXML
    void onDeleteButton_clicked(ActionEvent event) {
        try {
            List<Option> selected = mainTableView.getSelectionModel().getSelectedItems();
            for (Option option : selected) {
                optionCrudService.delete(option.getId());
            }

            clearDetails();
            reloadMainTable();
        } catch (RepositoryException | SQLException ex) {
            DialogBox.error(ex.getMessage());
        }
    }

    private void clearDetails() {
        idTextField.setText("");
        candIdTextField.setText("");
        depIdTextField.setText("");
        depCodeTextField.setText("");
    }

}
