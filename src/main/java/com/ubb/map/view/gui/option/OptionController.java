package com.ubb.map.view.gui.option;

import com.ubb.map.services.OptionCrudService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.inject.Inject;

public class OptionController {

    @Inject
    private OptionCrudService optionCrudService;

    @FXML
    private TextField idTextField;

}
