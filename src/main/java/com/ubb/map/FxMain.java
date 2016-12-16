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
        InputStream fxml = new BufferedInputStream(getClass().getResourceAsStream("/view/gui/fxml/MainView.fxml"));
        Parent root = fxmlLoader.load(fxml);

        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
}