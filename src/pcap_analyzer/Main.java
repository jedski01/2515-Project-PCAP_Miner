package pcap_analyzer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Main
 * ACIT 2515 Final Project
 * Main Class
 *
 * @author Jed Iquin A00790108
 * @author Patrick Rodriguez A00997571
 * @date 2017-04-02
 */

public class Main extends Application {

    public static String mainScreen = "mainScreen";
    public static String mainFXML = "MainScene.fxml";

    public static String pieChartScreen = "pieScreen";
    public static String pieChartFXML = "PieChart.fxml";

    public static String barChartScreen = "barScreen";
    public static String barChartFXML = "BarChart.fxml";


    @Override
    public void start(Stage primaryStage) throws Exception{

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Main.mainScreen, Main.mainFXML);
        mainContainer.loadScreen(Main.pieChartScreen, Main.pieChartFXML);
        mainContainer.loadScreen(Main.barChartScreen, Main.barChartFXML);

        mainContainer.setScreen(Main.mainScreen);

        primaryStage.setOnCloseRequest(event -> {
            mainContainer.getController(Main.mainScreen).handleCloseAction();
        });
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Super PCAP Analyzer");
        primaryStage.getIcons().add(new Image("network.png"));

    }
    public static void main(String[] args) {
        launch(args);
    }
}
