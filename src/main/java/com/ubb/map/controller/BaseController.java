package com.ubb.map.controller;

import com.ubb.map.domain.HasId;
import com.ubb.map.repository.db.OrmRepository;
import com.ubb.map.services.crud.BaseCrudService;
import com.ubb.map.services.crud.HydrationType;
import com.ubb.map.services.export.CountType;
import com.ubb.map.services.filters.PropertyFilter;
import com.ubb.map.services.filters.simple.SimpleFilter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by marius on 15.01.2017.
 */
public abstract class BaseController<Id, T extends HasId<Id>> implements Initializable {
    @FXML
    protected TextField perPageTextField;
    @FXML
    protected Pagination pagination;
    @FXML
    protected ComboBox<CountType> exportComboBox;
    protected TableView<T> mainTableView;
    protected ObservableList<T> entityObservableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pagination.setPageFactory(this::createPage);
        initializeMainTableView();
        mainTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        mainTableView.setEditable(true);
        exportComboBox.getItems().addAll(CountType.values());
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
            DialogBox.error(ex.getMessage(), ex);
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

    @FXML
    void onStartExportButton_clicked(ActionEvent event) {
        try {
            CountType countType = exportComboBox.getValue();
            List<T> items;
            if (countType == CountType.ALL) {
                items = getMainCrudService().getAll(getFilters());
            } else {
                items = entityObservableList;
            }
            getMainCrudService().hydrate(items, HydrationType.FULL);
            DialogBox.exportUsingThread(DialogBox.chooseDirectory(mainTableView.getScene()), items, getManagedEntity());
        } catch (Exception e) {
            DialogBox.error(e);
        }
    }

    protected abstract Class<T> getManagedEntity();

    protected abstract BaseCrudService<Id, T> getMainCrudService();

    protected abstract void initializeMainTableView();

    protected abstract List<PropertyFilter> getFilters();
}
