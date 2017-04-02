package pcap_analyzer;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
//        Parent root = FXMLLoader.load(getClass().getResource("pcap_analyzer.fxml"));
//        primaryStage.setTitle("Super PCAP Analyzer");
//        primaryStage.setScene(new Scene(root, 1204, 846));
//        primaryStage.show();
        //primaryStage.getIcons().add(new Image("network.png"));


    }
    public static void main(String[] args) {
        launch(args);
    }
}
