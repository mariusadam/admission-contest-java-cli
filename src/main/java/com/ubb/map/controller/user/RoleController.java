package com.ubb.map.controller.user;

import com.ubb.map.controller.BaseController;
import com.ubb.map.controller.DialogBox;
import com.ubb.map.domain.Candidate;
import com.ubb.map.domain.Resource;
import com.ubb.map.domain.Role;
import com.ubb.map.exception.RepositoryException;
import com.ubb.map.services.crud.BaseCrudService;
import com.ubb.map.services.crud.RoleCrudService;
import com.ubb.map.services.crud.UserService;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.controlsfx.control.CheckComboBox;

import javax.inject.Inject;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoleController extends BaseController<Integer, Role> {
    @FXML
    private TextField idTextField;

    @FXML
    private HBox createdAtFilterHBox;

    @FXML
    private GridPane detailsGridPane;

    @FXML
    private DatePicker createdAtMinDatePicker;

    @FXML
    private DatePicker updatedAtMinDatePicker;

    @FXML
    private Label lnTextField;

    @FXML
    private TextField idMaxTextField;

    @FXML
    private TextField idMatchTextField;

    @FXML
    private ComboBox<PropertyFilter> nameFilterComboBox;

    @FXML
    private DatePicker updatedAtMaxDatePicker;

    @FXML
    private DatePicker createdAtMaxDatePicker;

    @FXML
    private TextField importanceTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private HBox updatedAtFilterHBox;

    @FXML
    private ComboBox<PropertyFilter> createdAtFilterComboBox;

    @FXML
    private ComboBox<PropertyFilter> exportComboBox;

    @FXML
    private DatePicker createdAtMatchDatePicker;

    @FXML
    private TextField idMinTextField;

    @FXML
    private ComboBox<PropertyFilter> updatedAtFilterComboBox;

    @FXML
    private TextField perPageTextField;

    @FXML
    private HBox idFilterHBox;

    @FXML
    private DatePicker updatedAtMatchDatePicker;

    @FXML
    private ComboBox<PropertyFilter> idFilterComboBox;

    private CheckComboBox<Resource> resourcesCheckComboBox;

    @FXML
    private TextField nameMatchTextField;

    @Inject
    private RoleCrudService roleCrudService;

    @Inject
    private UserService userService;

    @FXML
    private TitledPane exportsPane;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idTextField.setEditable(false);
        idTextField.setDisable(true);
        exportsPane.setVisible(false);
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

        resourcesCheckComboBox = new CheckComboBox<>();
        resourcesCheckComboBox.getItems().addAll(Resource.values());
        detailsGridPane.add(resourcesCheckComboBox, 1, 2);
        super.initialize(location, resources);
    }

    @Override
    protected void initializeMainTableView() {
        TableColumn<Role, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Role, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(150);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(t -> {
            try {
                int index = t.getTablePosition().getRow();
                Role c = t.getTableView().getItems().get(index);
                c.setName(t.getNewValue());
                c = roleCrudService.update(c);
                t.getTableView().getItems().set(index, c);
            } catch (Exception e) {
                DialogBox.error(e.getMessage());
            }
        });

        TableColumn<Role, Number> importanceColumn = new TableColumn<>("Importance");
        importanceColumn.setMinWidth(150);
        importanceColumn.setCellValueFactory(new PropertyValueFactory<>("importance"));
        importanceColumn.setCellFactory(col -> new IntegerEditingCell());
        importanceColumn.setOnEditCommit(t -> {
            try {
                int index = t.getTablePosition().getRow();
                Role c = t.getTableView().getItems().get(index);
                c.setImportance(t.getNewValue().intValue());
                c = roleCrudService.update(c);
                t.getTableView().getItems().set(index, c);
            } catch (Exception e) {
                DialogBox.error(e.getMessage());
            }
        });

        TableColumn<Role, Date> createdAtColumn = new TableColumn<>();
        createdAtColumn.setText("Created at");
        createdAtColumn.setMinWidth(150);
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<Role, Date> updatedAtColumn = new TableColumn<>();
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
                importanceColumn,
                createdAtColumn,
                updatedAtColumn
        );
        mainTableView.
                getSelectionModel().
                selectedItemProperty().
                addListener((observable, oldValue, newValue) -> loadRole(newValue));

    }

    private void loadRole(Role newValue) {
        if (newValue != null) {
            idTextField.setText(newValue.getId().toString());
            nameTextField.setText(newValue.getName());
            importanceTextField.setText(newValue.getImportance().toString());
            resourcesCheckComboBox.getCheckModel().clearChecks();
            resourcesCheckComboBox.getItems().clear();
            resourcesCheckComboBox.getItems().addAll(Resource.values());
            Collection<Resource> selected = newValue.getResources();
            int len = resourcesCheckComboBox.getItems().size();
            for (int i = 0; i < len; i++) {
                resourcesCheckComboBox.getCheckModel().clearCheck(i);
                Resource r = resourcesCheckComboBox.getItems().get(i);
                if (selected.contains(r)) {
                    resourcesCheckComboBox.getCheckModel().check(i);
                }
            }
        }
    }

    @Override
    protected Class<Role> getManagedEntity() {
        return Role.class;
    }

    @Override
    protected BaseCrudService<Integer, Role> getMainCrudService() {
        return roleCrudService;
    }

    @Override
    protected List<PropertyFilter> getFilters() {
        return Stream.of(
                idFilterComboBox,
                nameFilterComboBox,
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
                nameFilterComboBox
        ).forEach(propertyFilterComboBox -> propertyFilterComboBox.getSelectionModel().clearSelection());
        Stream.of(
                idMatchTextField,
                idMinTextField,
                idMaxTextField,
                nameMatchTextField
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
            roleCrudService.create(
                    nameTextField.getText(),
                    importanceTextField.getText(),
                    resourcesCheckComboBox.getCheckModel().getCheckedItems()
            );
            reloadMainTable();
        } catch (Exception e) {
            DialogBox.error(e);
        }
    }

    @FXML
    void onUpdateButton_clicked(ActionEvent event) {
        try {
            roleCrudService.update(
                    idTextField.getText(),
                    nameTextField.getText(),
                    importanceTextField.getText(),
                    resourcesCheckComboBox.getCheckModel().getCheckedItems()
            );
            reloadMainTable();
        } catch (Exception e) {
            DialogBox.error(e);
        }
    }

    @FXML
    void onDeleteButton_clicked(ActionEvent event) {
        try {
            List<Role> selected = mainTableView.getSelectionModel().getSelectedItems();
            for (Role role: selected) {
                roleCrudService.delete(role.getId());
            }

            clearDetails();
            reloadMainTable();
        } catch (RepositoryException | SQLException ex) {
            DialogBox.error(ex.getMessage());
        }
    }

    private void clearDetails() {
        idTextField.clear();
        nameTextField.clear();
        importanceTextField.clear();
        resourcesCheckComboBox.getItems().clear();
    }
}
