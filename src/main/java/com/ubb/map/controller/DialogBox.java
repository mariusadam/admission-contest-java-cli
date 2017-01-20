package com.ubb.map.controller;

import com.ubb.map.services.export.ExportType;
import com.ubb.map.services.export.ExporterFactory;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import static javafx.scene.control.ButtonBar.ButtonData;

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

    public static <T> void exportUsingThread(String fileName, List<T> items, Class<T> entity) {
        //obtain the worker in the current thread so that exception is handled
        Task worker = ExporterFactory.createForFileName(fileName, items, entity);
        new Thread(new Task() {
            @Override
            protected Object call() throws Exception {
                Platform.runLater(() -> export(worker, fileName, items, entity));
                return true;
            }
        }).start();
    }

    protected static <T> void export(String fileName, List<T> items, Class<T> entity) {
        Task worker = ExporterFactory.createForFileName(fileName, items, entity);
        export(worker, fileName, items, entity);
    }

    protected static <T> void export(Task worker, String fileName, List<T> items, Class<T> entity) {
        // Create the custom dialog.
        Dialog dialog = new Dialog();
        GridPane grid = new GridPane();
        VBox mainContent = new VBox();
        // Set the button types.
        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);

        dialog.getDialogPane().setExpandableContent(grid);
        dialog.setTitle("Export Dialog");
        dialog.setHeaderText("Please wait while the data is being exported...");
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(mainContent);
        dialog.getDialogPane().setExpandableContent(grid);
        dialog.initModality(Modality.WINDOW_MODAL);
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(20, 150, 50, 10));

        // Enable/Disable ok button depending on whether export finished
        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        okButton.setDisable(true);
        cancelButton.setOnAction(event -> worker.cancel());

        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(700);
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(worker.progressProperty());
        // Do some validation (using the Java 8 lambda syntax).
        progressBar.progressProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.intValue() != 1.0);
        });
        mainContent.getChildren().addAll(progressBar);

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

//        grid.add(progressBar, 0, 0);
        grid.add(label, 0, 0);
        grid.add(textArea, 0, 1);

        worker.messageProperty().addListener(
                (observable, oldValue, newValue) -> textArea.appendText(newValue + System.lineSeparator()));
        Thread t = new Thread(worker);
        t.start();
        dialog.show();
    }
}