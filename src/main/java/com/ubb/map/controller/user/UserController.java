package com.ubb.map.controller.user;

import com.ubb.map.controller.BaseController;
import com.ubb.map.controller.DialogBox;
import com.ubb.map.domain.Role;
import com.ubb.map.domain.User;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.controlsfx.control.CheckComboBox;

import javax.inject.Inject;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserController extends BaseController<Integer, User> {
    @FXML
    private TextField idTextField;

    @FXML
    private HBox createdAtFilterHBox;

    @FXML
    private ComboBox<PropertyFilter> isActiveFilterComboBox;

    @FXML
    private DatePicker createdAtMinDatePicker;

    @FXML
    private ComboBox<PropertyFilter> lastNameFilterComboBox;

    @FXML
    private ComboBox<PropertyFilter> firstNameFilterComboBox;

    @FXML
    private DatePicker updatedAtMinDatePicker;

    @FXML
    private TextField fNameTextField;

    @FXML
    private Label lnTextField;

    @FXML
    private TextField idMaxTextField;

    @FXML
    private TextField idMatchTextField;

    @FXML
    private DatePicker updatedAtMaxDatePicker;

    @FXML
    private DatePicker createdAtMaxDatePicker;

    @FXML
    private CheckBox isActiveCheckBox;

    @FXML
    private HBox updatedAtFilterHBox;

    @FXML
    private ComboBox<PropertyFilter> createdAtFilterComboBox;

    @FXML
    private TextField lNameTextField;

    @FXML
    private CheckBox isActiveFilter;

    @FXML
    private TextField firstNameMatchTextField;

    @FXML
    private TextField lastNameMatchTextField;

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

    @FXML
    private GridPane detailsGridPane;

    @FXML
    private ComboBox<PropertyFilter> emailFilterComboBox;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField emailMatchTextField;

    @FXML
    private PasswordField passwordField;

    private CheckComboBox<Role> roleCheckComboBox;

    @Inject
    private UserService userService;

    @Inject
    private RoleCrudService roleCrudService;

    @FXML
    private TitledPane exportsPane;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idTextField.setEditable(false);
        exportsPane.setVisible(false);
        PropertyFilter nullFilter = new NullFilter();

        ValueProvider idProvider = () -> idMatchTextField.getText();
        ValueProvider idMinVP = () -> idMinTextField.getText();
        ValueProvider idMaxVP = () -> idMaxTextField.getText();
        idFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter("id", idProvider),
                new NotEqualsFilter("id", idProvider),
                new BetweenFilter("id", idMinVP, idMaxVP),
                new NotBetweenFilter("id", idMinVP, idMaxVP)
        );

        ValueProvider emailProvider = () -> emailMatchTextField.getText();
        emailFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter("email", emailProvider),
                new NotEqualsFilter("email", emailProvider),
                new ContainsFilter("email", emailProvider),
                new NotContainsFilter("email", emailProvider)
        );

        ValueProvider fnProvider = () -> firstNameMatchTextField.getText();
        firstNameFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter("first_name", fnProvider),
                new NotEqualsFilter("first_name", fnProvider),
                new ContainsFilter("first_name", fnProvider),
                new NotContainsFilter("first_name", fnProvider)
        );

        ValueProvider lnProvider = () -> lastNameMatchTextField.getText();
        lastNameFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter("last_name", lnProvider),
                new NotEqualsFilter("last_name", lnProvider),
                new ContainsFilter("last_name", lnProvider),
                new NotContainsFilter("last_name", lnProvider)
        );

        ValueProvider isActiveProvider = () -> isActiveFilter.isSelected() ? 1 : 0;
        isActiveFilterComboBox.getItems().addAll(
                nullFilter,
                new EqualsFilter("is_active", isActiveProvider),
                new NotEqualsFilter("is_active", isActiveProvider)
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

        roleCheckComboBox = new CheckComboBox<Role>();
        try {
            roleCheckComboBox.getItems().addAll(roleCrudService.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        detailsGridPane.add(roleCheckComboBox, 1, 6);

        super.initialize(location, resources);
    }

    @Override
    protected void initializeMainTableView() {
        TableColumn<User, Integer> idColumn = new TableColumn<>("Id");
        idColumn.setMinWidth(50);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, String> firstNameColumn = new TableColumn<>("First name");
        firstNameColumn.setMinWidth(150);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameColumn.setOnEditCommit(t -> {
            try {
                int index = t.getTablePosition().getRow();
                User c = t.getTableView().getItems().get(index);
                c.setFirstName(t.getNewValue());
                c = userService.update(c);
                t.getTableView().getItems().set(index, c);
            } catch (Exception e) {
                DialogBox.error(e.getMessage());
            }
        });

        TableColumn<User, String> lastNameColumn = new TableColumn<>("Last name");
        lastNameColumn.setMinWidth(150);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setOnEditCommit(t -> {
            try {
                int index = t.getTablePosition().getRow();
                User c = t.getTableView().getItems().get(index);
                c.setLastName(t.getNewValue());
                c = userService.update(c);
                t.getTableView().getItems().set(index, c);
            } catch (Exception e) {
                DialogBox.error(e.getMessage());
            }
        });

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setMinWidth(150);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, Boolean> isActieColumn = new TableColumn<>("Is active");
        isActieColumn.setMinWidth(200);
        isActieColumn.setCellValueFactory(new PropertyValueFactory<>("isActive"));
        isActieColumn.setCellFactory(param -> new BooleanCell());
        isActieColumn.setOnEditCommit(t -> {
            try {
                int index = t.getTablePosition().getRow();
                User c = t.getTableView().getItems().get(index);
                c.setIsActive(t.getNewValue());
                c = userService.update(c);
                t.getTableView().getItems().set(index, c);
            } catch (Exception e) {
                DialogBox.error(e.getMessage());
            }
        });

        TableColumn<User, Date> lastLogin = new TableColumn<>("Last login");
        lastLogin.setMinWidth(200);
        lastLogin.setCellValueFactory(new PropertyValueFactory<>("lastLogin"));

        TableColumn<User, Date> createdAtColumn = new TableColumn<>();
        createdAtColumn.setText("Created at");
        createdAtColumn.setMinWidth(150);
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        TableColumn<User, Date> updatedAtColumn = new TableColumn<>();
        updatedAtColumn.setText("Updated at");
        updatedAtColumn.setMinWidth(150);
        updatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));


        entityObservableList = FXCollections.observableArrayList();
        mainTableView = new TableView<>();
        mainTableView.setItems(entityObservableList);
        //noinspection unchecked
        mainTableView.getColumns().addAll(
                idColumn,
                emailColumn,
                firstNameColumn,
                lastNameColumn,
                isActieColumn,
                lastLogin,
                createdAtColumn,
                updatedAtColumn
        );
        mainTableView.
                getSelectionModel().
                selectedItemProperty().
                addListener((observable, oldValue, newValue) -> loadUser(newValue));

    }
    class BooleanCell extends TableCell<User, Boolean> {
        private CheckBox checkBox;
        public BooleanCell() {
            checkBox = new CheckBox();
            checkBox.setDisable(true);
            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(isEditing())
                        commitEdit(newValue == null ? false : newValue);
                }
            });
            this.setGraphic(checkBox);
            this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            this.setEditable(true);
        }
        @Override
        public void startEdit() {
            super.startEdit();
            if (isEmpty()) {
                return;
            }
            checkBox.setDisable(false);
            checkBox.requestFocus();
        }
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            checkBox.setDisable(true);
        }
        public void commitEdit(Boolean value) {
            super.commitEdit(value);
            checkBox.setDisable(true);
        }
        @Override
        public void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEmpty()) {
                checkBox.setSelected(item);
            }
        }
    }

    private void loadUser(User user) {
        if (user != null) {
            idTextField.setText(user.getId().toString());
            emailTextField.setText(user.getEmail());
            fNameTextField.setText(user.getFirstName());
            lNameTextField.setText(user.getLastName());
            isActiveCheckBox.setSelected(user.getIsActive());
            roleCheckComboBox.getItems().clear();
            Collection<Role> selectedRoles;
            try {
                roleCheckComboBox.getItems().addAll(roleCrudService.getAll());
                roleCheckComboBox.getCheckModel().clearChecks();
                selectedRoles = userService.getRoles(user);
                int len = roleCheckComboBox.getItems().size();
                for (int i = 0; i < len; i++) {
                    Role r = roleCheckComboBox.getItems().get(i);
                    if (selectedRoles.contains(r)) {
                        roleCheckComboBox.getCheckModel().check(i);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                firstNameFilterComboBox,
                lastNameFilterComboBox
        ).forEach(propertyFilterComboBox -> propertyFilterComboBox.getSelectionModel().clearSelection());
        Stream.of(
                idMatchTextField,
                idMinTextField,
                idMaxTextField

        ).forEach(textField -> textField.setText(""));
        Stream.of(
                createdAtMatchDatePicker,
                createdAtMinDatePicker,
                createdAtMaxDatePicker,
                updatedAtMatchDatePicker,
                updatedAtMinDatePicker,
                updatedAtMaxDatePicker
        ).forEach(datePicker -> datePicker.setValue(null));

        isActiveFilterComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    void onAddButton_clicked(ActionEvent event) {
        try {
            userService.create(
                    emailTextField.getText(),
                    passwordField.getText(),
                    fNameTextField.getText(),
                    lnTextField.getText(),
                    isActiveCheckBox.isSelected(),
                    roleCheckComboBox.getCheckModel().getCheckedItems()
            );
            reloadMainTable();
        } catch (Exception e) {
            DialogBox.error(e);
        }
    }

    @FXML
    void onUpdateButton_clicked(ActionEvent event) {
        try {
            int i = 0;
            User u = userService.update(
                    idTextField.getText(),
                    emailTextField.getText(),
                    fNameTextField.getText(),
                    lnTextField.getText(),
                    isActiveCheckBox.isSelected(),
                    roleCheckComboBox.getCheckModel().getCheckedItems()
            );
            if (!passwordField.getText().isEmpty()) {
                u.setPlainPassword(passwordField.getText());
                userService.updatePassword(u);
            }
            reloadMainTable();
        } catch (Exception e) {
            DialogBox.error(e);
        }
    }

    @FXML
    void onDeleteButton_clicked(ActionEvent event) {
        try {
            List<User> selected = mainTableView.getSelectionModel().getSelectedItems();
            for (User u : selected) {
                userService.delete(u.getId());
            }
            reloadMainTable();
        } catch (RepositoryException | SQLException ex) {
            DialogBox.error(ex.getMessage());
        }
    }

    @FXML
    void onStartExportButton_clicked(ActionEvent event) {

    }

    @Override
    protected Class<User> getManagedEntity() {
        return User.class;
    }

    @Override
    protected BaseCrudService<Integer, User> getMainCrudService() {
        return userService;
    }

    @Override
    protected List<PropertyFilter> getFilters() {
        return Stream.of(
                idFilterComboBox,
                firstNameFilterComboBox,
                lastNameFilterComboBox,
                isActiveFilterComboBox,
                createdAtFilterComboBox,
                updatedAtFilterComboBox
        ).map(propertyFilterComboBox -> propertyFilterComboBox.getSelectionModel().getSelectedItem()
        ).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
