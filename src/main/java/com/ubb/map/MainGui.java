package com.ubb.map;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jboss.weld.environment.se.*;

public class MainGui extends Application {
    private Weld weld;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        weld = new Weld();
    }

    @Override
    public void start( Stage stage ) throws Exception {
        weld.initialize().instance().select(FxMain.class ).get().start( stage, getParameters() );
    }

    @Override
    public void stop() {
        weld.shutdown();
    }
}