package com.ubb.map.view.gui;

import com.ubb.map.domain.Resource;
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

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

@Singleton
public class MainController implements Initializable {
    @Inject
    private FXMLLoader fxmlLoader;
    @Inject
    private CandidatesView candidatesView;
    @Inject
    private AclService acl;
    @Inject
    private UserService userService;

    @FXML
    private TabPane tabPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private VBox loginPane;
    @FXML
    private Text loginMessage;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;

    private User       currentUser;

    public MainController() {
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
    void onLoginBtn_clicked(ActionEvent event) {
        String email = this.emailTextField.toString();
        String plainPasswd = this.passwordTextField.toString();
        try {
            User user = this.userService.getUserByEmailAndPassword(email, plainPasswd);
            if (user == null) {
                this.loginMessage.textProperty().setValue("Invalid email or password!.");
            } else {
                this.currentUser = user;
                this.loginMessage.textProperty().setValue("Login success");
                this.loginPane.setVisible(false);
                this.borderPane.setVisible(true);
            }
        } catch (SQLException e) {
            this.loginMessage.textProperty().setValue(e.getMessage());
        }
    }

    private Tab getCandidatesTab() {
        return new Tab("Candidates", candidatesView.getPane());
    }

    private Tab getDepartmentsTab() {
        File file = new File("src/main/java/com/ubb/map/view/gui/department/DepartmentView.fxml");

        try {
            Parent root = fxmlLoader.load(new FileInputStream(file));
            return new Tab("Departments", root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Tab getOptionsTab() {
        File file = new File("src/main/java/com/ubb/map/view/gui/option/OptionView.fxml");

        try {
            Parent root = fxmlLoader.load(new FileInputStream(file));
            return new Tab("Options", root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Tab getUsersTab() {
        File file = new File("src/main/java/com/ubb/map/view/gui/user/UserView.fxml");

        try {
            Parent root = fxmlLoader.load(new FileInputStream(file));
            return new Tab("Users", root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Tab getRolesTab() {
        File file = new File("src/main/java/com/ubb/map/view/gui/user/RoleView.fxml");

        try {
            Parent root = fxmlLoader.load(new FileInputStream(file));
            return new Tab("Roles", root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
