package com.ubb.map.controller.option;

import com.ubb.map.controller.BaseController;
import com.ubb.map.domain.Option;
import com.ubb.map.services.crud.BaseCrudService;
import com.ubb.map.services.crud.OptionCrudService;
import com.ubb.map.services.filters.PropertyFilter;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Singleton
public class OptionController extends BaseController<Integer, Option> {
    @FXML private TextField idTextField;

    @Inject private OptionCrudService optionCrudService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

    @Override
    protected void initializeMainTableView() {

    }

    @Override
    protected BaseCrudService<Integer, Option> getMainCrudService() {
        return null;
    }

    @Override
    protected List<PropertyFilter> getFilters() {
        return null;
    }
}
