package com.ubb.map.view.gui.department;

import java.net.URL;
import java.util.ResourceBundle;

import com.ubb.map.services.DepartmentCrudService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import javax.inject.Inject;

public class DepartmentController implements Initializable{

    @Inject
    private DepartmentCrudService departmentCrudService;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="idTextField"
    private TextField idTextField; // Value injected by FXMLLoader

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
    }
}
