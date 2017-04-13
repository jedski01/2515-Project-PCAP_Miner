package pcap_analyzer;

import conversation.ConversationModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

/**
 * ConversationController
 * ACIT 2515 Final Project
 * Controller Super Class for every conversation
 *
 * @author Jed Iquin A00790108
 * @author Patrick Rodriguez A00997571
 * @date 2017-04-02
 */

public class ConversationController implements Initializable{

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<ConversationModel> tableView = new TableView<>();

    @FXML
    private TableColumn<ConversationModel, String> addressA;
    @FXML
    private TableColumn<ConversationModel, String> addressB;
    @FXML
    private TableColumn<ConversationModel, Integer> totalPackets;
    @FXML
    private TableColumn<ConversationModel, Integer> totalBytes;
    @FXML
    private TableColumn<ConversationModel, Integer> packetsAB;
    @FXML
    private TableColumn<ConversationModel, Integer> packetsBA;
    @FXML
    private TableColumn<ConversationModel, Integer> byteAB;
    @FXML
    private TableColumn<ConversationModel, Integer> bytesBA;
    @FXML
    private TableColumn<ConversationModel, Double> duration;
    @FXML
    private TableColumn<ConversationModel, Double> bpsAB;
    @FXML
    private TableColumn<ConversationModel, Double> bpsBA;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addressA.setCellValueFactory(cellData -> cellData.getValue().addressAProperty());
        addressB.setCellValueFactory(cellData -> cellData.getValue().addressBProperty());

        totalPackets.setCellValueFactory(cellData -> cellData.getValue().packetsProperty().asObject());
        totalBytes.setCellValueFactory(cellData -> cellData.getValue().bytesProperty().asObject());

        packetsAB.setCellValueFactory(cellData -> cellData.getValue().packetsAToBProperty().asObject());
        packetsBA.setCellValueFactory(cellData -> cellData.getValue().packetsBToAProperty().asObject());

        byteAB.setCellValueFactory(cellData -> cellData.getValue().bytesAToBProperty().asObject());
        bytesBA.setCellValueFactory(cellData -> cellData.getValue().bytesBToAProperty().asObject());

        duration.setCellValueFactory(cellData -> cellData.getValue().durationProperty().asObject());
        duration.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.0000"), false));

        bpsAB.setCellValueFactory(cellData -> cellData.getValue().bpsAToBProperty().asObject());
        bpsAB.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00"), true));

        bpsBA.setCellValueFactory(cellData -> cellData.getValue().bpsBToAProperty().asObject());
        bpsBA.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00"), true));

    }

    public void handleCloseAction() {

        ((Stage)rootPane.getScene().getWindow()).close();
    }

    @FXML
    public void setData(ObservableList<ConversationModel> convModel) {
        tableView.setItems(convModel);
    }
}
