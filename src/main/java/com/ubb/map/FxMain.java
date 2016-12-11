package com.ubb.map;

import javafx.application.Application.Parameters;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javax.inject.Inject;
import java.io.*;

public class FxMain
{
    @Inject
    private FXMLLoader fxmlLoader;

    public void start( Stage stage, Parameters parameters ) throws Exception
    {
        File file = new File("src/main/java/com/ubb/map/view/gui/MainView.fxml");
        InputStream fxml = new BufferedInputStream(new FileInputStream(file));
        Parent root = fxmlLoader.load(fxml);

        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
}