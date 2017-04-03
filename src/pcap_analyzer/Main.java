package pcap_analyzer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static String mainScreen = "mainScreen";
    public static String mainFXML = "MainScene.fxml";

    public static String pieChartScreen = "pieScreen";
    public static String pieChartFXML = "PieChart.fxml";

    public static String barChartScreen = "barScreen";
    public static String barChartFXML = "BarChart.fxml";

    //new stages
    public static String defaultConvFXML = "DefaultConversation.fxml";
    public static String ipv4ConvFXML = "IPv4Conversation.fxml";
    public static String ipv6ConvFXML = "IPv6Conversation.fxml";
    public static String tcpConvFXML = "TCPConversation.fxml";
    public static String udpConvFXML = "UDPConversation.fxml";

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

    }
    public static void main(String[] args) {
        launch(args);
    }
}
