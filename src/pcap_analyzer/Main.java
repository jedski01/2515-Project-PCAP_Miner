package pcap_analyzer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("pcap_analyzer.fxml"));
        primaryStage.setTitle("Super PCAP Analyzer");
        primaryStage.setScene(new Scene(root, 1059, 686));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
