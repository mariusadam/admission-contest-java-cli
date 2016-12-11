package com.ubb.map.view.gui;

import com.ubb.map.domain.Resource;
import com.ubb.map.domain.User;
import com.ubb.map.helper.ContainerFactory;
import com.ubb.map.helper.ServiceContainer;
import com.ubb.map.services.AclService;
import com.ubb.map.view.gui.candidate.CandidatesView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by marius on 08.12.2016.
 */
public class MainController implements Initializable {

    private AclService acl;
    private User       currentUser;
    private ServiceContainer container;

    @FXML
    private TabPane tabPane;

    public MainController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.container = ContainerFactory.getInstance().getServiceContainer();
        this.acl = this.container.getAclService();
        this.currentUser = new User();
        this.currentUser.setEmail("user@mail.com");
        this.currentUser.setId(123);


        if (this.acl.isAllowed(this.currentUser, Resource.CANDIDATE)) {
            this.tabPane.getTabs().add(this.getCandidatesTab());
        }

        if (this.acl.isAllowed(this.currentUser, Resource.DEPARTMENT)) {
            this.tabPane.getTabs().add(this.getDepartmentsTab());
        }

        if (this.acl.isAllowed(this.currentUser, Resource.OPTION)) {
            this.tabPane.getTabs().add(this.getOptionsTab());
        }

        if (this.acl.isAllowed(this.currentUser, Resource.USER)) {
            this.tabPane.getTabs().add(this.getUsersTab());
        }

        if (this.acl.isAllowed(this.currentUser, Resource.ROLE)) {
            this.tabPane.getTabs().add(this.getRolesTab());
        }
    }

    private Tab getCandidatesTab() {
        CandidatesView candidatesView = new CandidatesView(container.getCandidateConttroller());
        return new Tab("Candidates", candidatesView.getPane());
    }

    private Tab getDepartmentsTab() {
        File file = new File("src/main/java/com/ubb/map/view/gui/department/DepartmentView.fxml");

        try {
            Parent root = FXMLLoader.load(file.toURI().toURL());
            return new Tab("Departments", root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Tab getOptionsTab() {
        File file = new File("src/main/java/com/ubb/map/view/gui/option/OptionView.fxml");

        try {
            Parent root = FXMLLoader.load(file.toURI().toURL());
            return new Tab("Options", root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Tab getUsersTab() {
        File file = new File("src/main/java/com/ubb/map/view/gui/user/UserView.fxml");

        try {
            Parent root = FXMLLoader.load(file.toURI().toURL());
            return new Tab("Users", root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Tab getRolesTab() {
        File file = new File("src/main/java/com/ubb/map/view/gui/user/RoleView.fxml");

        try {
            Parent root = FXMLLoader.load(file.toURI().toURL());
            return new Tab("Roles", root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
