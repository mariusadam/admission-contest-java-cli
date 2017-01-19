package com.ubb.map.controller;

import com.ubb.map.services.export.ExportType;
import com.ubb.map.services.export.ExporterFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import static javafx.scene.control.ButtonBar.*;

/**
 * Created by marius on 11/20/2016.
 */
public class DialogBox {

    //TODO make the alert box more user friendly
    public static Optional<ButtonType> display(AlertType type, String title, String message, Exception ex) {
        Alert alertWindow = new Alert(type);

        alertWindow.initModality(Modality.APPLICATION_MODAL);
        alertWindow.setTitle(title);
        alertWindow.setHeaderText(null);
        alertWindow.setContentText(message);
        alertWindow.getDialogPane().setPrefWidth(500);
        if (ex != null) {
            // Create expandable Exception.
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String exceptionText = sw.toString();

            Label label = new Label("The exception stacktrace was:");

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            // Set expandable Exception into the dialog pane.
            alertWindow.getDialogPane().setExpandableContent(expContent);

        }
        return alertWindow.showAndWait();
    }

    public static Optional<ButtonType> error(Exception ex) {
        return error(ex.getMessage(), ex);
    }

    public static Optional<ButtonType> error(String message) {
        return error(message, null);
    }

    public static Optional<ButtonType> error(String message, Exception ex) {
        return display(AlertType.ERROR, "Error!", message, ex);
    }

    public static String chooseDirectory(Scene parentScene) throws Exception {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open destination folder...");
        fc.getExtensionFilters().addAll(
                new ExtensionFilter(ExportType.PDF.toString(), ExportType.PDF.getExtensionPattern()),
                new ExtensionFilter(ExportType.CSV.toString(), ExportType.CSV.getExtensionPattern()),
                new ExtensionFilter(ExportType.XML.toString(), ExportType.XML.getExtensionPattern()),
                new ExtensionFilter(ExportType.JSON.toString(), ExportType.JSON.getExtensionPattern())
        );
        File file = fc.showSaveDialog(parentScene.getWindow());
        if (file == null) {
            throw new Exception("You have not selected a filename for export!");
        }

        return file.getAbsolutePath();
    }


    public static <T> void export(String fileName, List<T> items, Class<T> entity) {
        Task worker = ExporterFactory.createForFileName(fileName, items, entity);
        assert worker != null;
        // Create the custom dialog.
        Dialog dialog = new Dialog();
        dialog.setTitle("Export Dialog");
        dialog.setHeaderText("Please wait while the data is being exported...");
        // Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Username:"), 0, 0);
        ProgressBar progressBar = new ProgressBar(0);
        //add progress bar here
        grid.add(progressBar, 0, 0);

        // Enable/Disable ok button depending on whether export finished
        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                worker.cancel();
            }
        });
        int max = items.size();
        // Do some validation (using the Java 8 lambda syntax).
        progressBar.progressProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.intValue() == max);
        });

        dialog.getDialogPane().setContent(grid);

//        dialog.setResultConverter(dialogButton -> {
//            if (dialogButton == okButtonType) {
//                return new Pair<>(username.getText(), password.getText());
//            }
//            return null;
//        });

        Label label = new Label("Details:");

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane.setVgrow(progressBar, Priority.ALWAYS);
        GridPane.setHgrow(progressBar, Priority.ALWAYS);

        grid.add(label, 0, 1);
        grid.add(textArea, 0, 2);

        progressBar.setPrefWidth(1000);
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(worker.progressProperty());
        worker.messageProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                textArea.appendText(newValue + System.lineSeparator());
            }
        });
        Thread t = new Thread(worker);
        t.start();
        dialog.showAndWait();
        try {
            t.join();
        } catch (InterruptedException e) {
            error(e);
        }

    }
}