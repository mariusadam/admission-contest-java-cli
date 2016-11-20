import helper.ServiceContainer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import view.gui.CandidatesView;

public class MainGui extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ServiceContainer container = new ServiceContainer("src/main/resources/config/config.yml");

        primaryStage.setTitle("Tabs");
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.GREY);

        TabPane tabPane = new TabPane();

        BorderPane borderPane = new BorderPane();

        Tab tab = new Tab();
        tab.setText("Candidates Tab");
        HBox hbox = new HBox();
        hbox.getChildren().add(getCandidatePane(container));
        hbox.setAlignment(Pos.CENTER);
        tab.setContent(hbox);
        tabPane.getTabs().add(tab);

        Tab tab2 = new Tab();
        tab2.setText("Dpartments Tab");
        HBox hbox2 = new HBox();
        hbox2.getChildren().add(getDepartmentPane(container));
        hbox2.setAlignment(Pos.CENTER);
        tab2.setContent(hbox2);
        tabPane.getTabs().add(tab2);

        Tab tab3 = new Tab();
        tab3.setText("Options Tab");
        HBox hbox3 = new HBox();
        hbox3.getChildren().add(getOptionPane(container));
        hbox3.setAlignment(Pos.CENTER);
        tab3.setContent(hbox3);
        tabPane.getTabs().add(tab3);

        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pane getCandidatePane(ServiceContainer container) {
        CandidatesView candidatesView = new CandidatesView(container.getCandidateConttroller());

        return candidatesView.getPane();
    }

    private Pane getDepartmentPane(ServiceContainer container) {
        GridPane gridPane = new GridPane();
        gridPane.getChildren().add(new Label("Departments"));
        gridPane.setAlignment(Pos.CENTER);

        return gridPane;
    }

    private Pane getOptionPane(ServiceContainer container) {
        GridPane gridPane = new GridPane();
        gridPane.getChildren().add(new Label("Options"));
        gridPane.setAlignment(Pos.CENTER);

        return gridPane;
    }

}