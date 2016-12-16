package com.ubb.map.view.gui;

import com.ubb.map.domain.User;
import com.ubb.map.services.AclService;
import com.ubb.map.services.UserService;
import com.ubb.map.view.gui.candidate.CandidatesView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.util.ServiceLoader;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;

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
    @FXML private TextField passwordTextField;

    private Map<String, Tab> createdTabs;
    private User       currentUser;
    public MainController() {
        this.createdTabs = new Hashtable<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loginMessage.textProperty().setValue("");
        this.currentUser  = null;
        this.borderPane.setVisible(false);
        this.loginPane.setVisible(true);
//        this.currentUser = new User();
//        this.currentUser.setEmail("user@mail.com");
//        this.currentUser.setId(123);
//
//
//        if (this.acl.isAllowed(this.currentUser, Resource.CANDIDATE)) {
//            this.tabPane.getTabs().add(this.getCandidatesTab());
//        }
//
//        if (this.acl.isAllowed(this.currentUser, Resource.DEPARTMENT)) {
//            this.tabPane.getTabs().add(this.getDepartmentsTab());
//        }
//
//        if (this.acl.isAllowed(this.currentUser, Resource.OPTION)) {
//            this.tabPane.getTabs().add(this.getOptionsTab());
//        }
//
//        if (this.acl.isAllowed(this.currentUser, Resource.USER)) {
//            this.tabPane.getTabs().add(this.getUsersTab());
//        }
//
//        if (this.acl.isAllowed(this.currentUser, Resource.ROLE)) {
//            this.tabPane.getTabs().add(this.getRolesTab());
//        }
    }

    @FXML
    private void onLoginBtn_clicked(ActionEvent event) {
        String email = this.emailTextField.toString();
        String plainPasswd = this.passwordTextField.toString();
        try {
            User user = this.userService.getUserByEmailAndPassword(email, plainPasswd);
            if (user != null) {
                this.loginMessage.textProperty().setValue("Invalid email or password!.");
            } else {
//                this.currentUser = user;
                this.loginMessage.textProperty().setValue("Login success");
                this.loginPane.setVisible(false);
                this.borderPane.setVisible(true);
            }
        } catch (SQLException e) {
            this.loginMessage.textProperty().setValue(e.getMessage());
        }
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
