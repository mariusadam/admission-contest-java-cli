package com.ubb.map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class MainGui extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        File file = new File("src/main/java/com/ubb/map/view/gui/MainView.fxml");

        Parent root = FXMLLoader.load(file.toURI().toURL());
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}