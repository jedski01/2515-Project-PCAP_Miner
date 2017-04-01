package pcap_analyzer;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @FXML
    public MenuItem exit;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("pcap_analyzer.fxml"));
        primaryStage.setTitle("Super PCAP Analyzer");
        primaryStage.setScene(new Scene(root, 1204, 846));
        primaryStage.show();
        primaryStage.getIcons().add(new Image("network.png"));

    }
    public static void main(String[] args) {
        launch(args);
    }
}
