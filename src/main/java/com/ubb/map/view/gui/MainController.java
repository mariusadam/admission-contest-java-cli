package com.ubb.map.view.gui;

import com.ubb.map.domain.Resource;
import com.ubb.map.domain.User;
import com.ubb.map.services.AclService;
import com.ubb.map.services.UserService;
import com.ubb.map.view.gui.candidate.CandidatesView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.jboss.weld.environment.se.WeldContainer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

@Singleton
public class MainController implements Initializable {
    private final static String DEPARTMENTS_TAB = "Departments";
    private final static String CANDIDATES_TAB = "Candidates";
    private final static String OPTIONS_TAB = "Options";

    @Inject private WeldContainer weldContainer;
    @Inject private CandidatesView candidatesView;
    @Inject private AclService acl;
    @Inject private UserService userService;

    @FXML private TabPane tabPane;
    @FXML private StackPane stackPane;
    @FXML private BorderPane borderPane;
    @FXML private VBox loginPane;
    @FXML private Text loginMessage;
    @FXML private TextField emailTextField;
    @FXML private PasswordField passwordTextField;

    @FXML private MenuBar menuBar;
    @FXML private Menu admissionMenu;
    @FXML private MenuItem optionsMenuItem;
    @FXML private MenuItem candidatesMenuItem;
    @FXML private MenuItem departmentsMenuItem;
    @FXML private Menu administrationMenu;
    @FXML private MenuItem usersMenuItem;
    @FXML private MenuItem rolesMenuIteam;

    private Map<String, Tab> createdTabs;
    private User currentUser;
    public MainController() {
        this.createdTabs = new Hashtable<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.initializeLogin();
        emailTextField.setText("a@b.com");
        passwordTextField.setText("abcd1234");
    }

    private void initializeLogin()
    {
        this.loginMessage.textProperty().setValue("");
        this.currentUser  = null;
        this.borderPane.setVisible(false);
        this.loginPane.setVisible(true);
    }

    @FXML
    private void onLoginBtn_clicked(ActionEvent event) {
        String email = this.emailTextField.getText();
        String plainPasswd = this.passwordTextField.getText();
        try {
            User user = this.userService.getUserByEmailAndPassword(email, plainPasswd);
            if (user == null) {
                this.loginMessage.textProperty().setValue("Invalid email or password!.");
            } else {
                this.currentUser = user;
                this.loginMessage.textProperty().setValue("Login success");
                this.configureMenuAccess();
                this.loginPane.setVisible(false);
                this.borderPane.setVisible(true);
            }
        } catch (SQLException e) {
            this.loginMessage.textProperty().setValue(e.getMessage());
        }
    }

    private void configureMenuAccess() {
        boolean showAdmission = false;
        boolean showAdministration = false;

        if (acl.isAllowed(currentUser, Resource.OPTION)) {
            showAdmission = true;
        } else {
            admissionMenu.getItems().remove(optionsMenuItem);
        }

        if (acl.isAllowed(currentUser, Resource.CANDIDATE)) {
            showAdmission = true;
        } else {
            admissionMenu.getItems().remove(candidatesMenuItem);
        }

        if (acl.isAllowed(currentUser, Resource.DEPARTMENT)) {
            showAdmission = true;
        } else {
            admissionMenu.getItems().remove(departmentsMenuItem);
        }

        if (acl.isAllowed(currentUser, Resource.USER)) {
            showAdministration = true;
        } else {
            administrationMenu.getItems().remove(usersMenuItem);
        }

        if (acl.isAllowed(currentUser, Resource.ROLE)) {
            showAdministration = true;
        } else {
            administrationMenu.getItems().remove(rolesMenuIteam);
        }

        if (!showAdmission) {
            menuBar.getMenus().remove(admissionMenu);
        }

        if (!showAdministration) {
            menuBar.getMenus().remove(administrationMenu);
        }
    }

    @FXML
    private void onLoginAsOtherUserMenuItem_clicked(ActionEvent event)
    {
        this.createdTabs.clear();
        this.tabPane.getTabs().clear();
        this.initializeLogin();
    }

    @FXML
    private void onLogoutAndExitMenuItem_clicked(ActionEvent event)
    {
        Platform.exit();
    }

    private Tab getCandidatesTab() {
        if (!this.createdTabs.containsKey(CANDIDATES_TAB)) {
            this.createdTabs.put(CANDIDATES_TAB, new Tab(CANDIDATES_TAB, candidatesView.getPane()));
        }

        return this.createdTabs.get(CANDIDATES_TAB);
    }

    private Tab getDepartmentsTab() {
        try {
            if (!this.createdTabs.containsKey(DEPARTMENTS_TAB)) {
                Parent root = getFXMLLoader().load(getClass().getResourceAsStream("/view/gui/fxml/DepartmentView.fxml"));
                this.createdTabs.put(DEPARTMENTS_TAB, new Tab(DEPARTMENTS_TAB, root));
            }

            return this.createdTabs.get(DEPARTMENTS_TAB);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Tab getOptionsTab() {
        try {
            if (!this.createdTabs.containsKey(OPTIONS_TAB)) {
                Parent root = getFXMLLoader().load(getClass().getResourceAsStream("/view/gui/fxml/OptionView.fxml"));
                this.createdTabs.put(OPTIONS_TAB, new Tab(OPTIONS_TAB, root));
            }

            return this.createdTabs.get(OPTIONS_TAB);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Tab getUsersTab() {
        try {
            Parent root = getFXMLLoader().load(getClass().getResourceAsStream("/view/gui/fxml/OptionView.fxml"));
            return new Tab("Users", root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Tab getRolesTab() {
        try {
            Parent root = getFXMLLoader().load(getClass().getResourceAsStream("/view/gui/fxml/RoleView.fxml"));
            return new Tab("Roles", root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void ensureTabAdded(Tab tab) {
        this.tabPane.setVisible(true);
        this.tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        if (!this.tabPane.getTabs().contains(tab)) {
            tab.closableProperty().setValue(true);
            this.tabPane.getTabs().add(tab);
        }
    }

    @FXML
    void onManageOptionsMenuItem_clicked(ActionEvent event) {
        this.ensureTabAdded(getOptionsTab());
    }

    @FXML
    void onManageCandidatesMenuItem_clicked(ActionEvent event) {
        this.ensureTabAdded(getCandidatesTab());
    }

    @FXML
    void onManageDepartmentsMenuItem_clicked(ActionEvent event) {
        this.ensureTabAdded(getDepartmentsTab());
    }

    private FXMLLoader getFXMLLoader() {
        return this.weldContainer.instance().select(FXMLLoader.class).get();
    }
}
