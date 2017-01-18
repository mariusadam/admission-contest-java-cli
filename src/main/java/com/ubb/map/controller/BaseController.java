package com.ubb.map.controller;

import com.ubb.map.domain.HasId;
import com.ubb.map.repository.db.OrmRepository;
import com.ubb.map.services.crud.BaseCrudService;
import com.ubb.map.services.filters.PropertyFilter;
import com.ubb.map.services.filters.simple.SimpleFilter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by marius on 15.01.2017.
 */
public abstract class BaseController<Id, T extends HasId<Id>> implements Initializable {
    @FXML protected TextField perPageTextField;
    @FXML protected Pagination pagination;

    protected TableView<T> mainTableView;
    protected ObservableList<T> entityObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pagination.setPageFactory(this::createPage);
        initializeMainTableView();
        mainTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        mainTableView.setEditable(true);
    }

    private Node createPage(int pageIndex) {
        reloadMainTable(pageIndex + 1);
        return new BorderPane(mainTableView);
    }

    protected void reloadMainTable() {
        reloadMainTable(pagination.getCurrentPageIndex() + 1);
    }

    private void reloadMainTable(Integer pageIndex) {
        entityObservableList.clear();

        int perPage = OrmRepository.PAGE_SIZE;
        try {
            perPage = Integer.parseInt(perPageTextField.getText());
        } catch (Exception ignored) {

        }
        try {
            entityObservableList.addAll(getMainCrudService().getFiltered(getFilters(), pageIndex, perPage));
            pagination.setPageCount(getMainCrudService().getNrOfPages(getFilters(), perPage));
        } catch (Exception ex) {
            AlertBox.error(ex.getMessage());
        }
    }

    protected void ensureCorrectFieldsAreVisible(PropertyFilter filter, HBox hBoxContainer, Control textField) {
        if (filter == null) {
            return;
        }

        if (filter instanceof SimpleFilter) {
            hBoxContainer.setVisible(false);
            textField.setVisible(true);
        } else {
            textField.setVisible(false);
            hBoxContainer.setVisible(true);
        }
    }

    @FXML
    void onSearchButton_clicked(ActionEvent event) {
        reloadMainTable();
    }

    protected abstract BaseCrudService<Id, T> getMainCrudService();

    protected abstract void initializeMainTableView();

    protected abstract List<PropertyFilter> getFilters();
}
