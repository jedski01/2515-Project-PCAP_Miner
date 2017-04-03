package pcap_analyzer;

import conversation.ConversationModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

/**
 * Created by Jed on 2017-04-02.
 */
public class ConversationController implements Initializable{

    @FXML
    public TableView<ConversationModel> tableView = new TableView<>();

    @FXML
    public TableColumn<ConversationModel, String> addressA;
    @FXML
    public TableColumn<ConversationModel, String> addressB;
    @FXML
    public TableColumn<ConversationModel, Integer> totalPackets;
    @FXML
    public TableColumn<ConversationModel, Integer> totalBytes;
    @FXML
    public TableColumn<ConversationModel, Integer> packetsAB;
    @FXML
    public TableColumn<ConversationModel, Integer> packetsBA;
    @FXML
    public TableColumn<ConversationModel, Integer> byteAB;
    @FXML
    public TableColumn<ConversationModel, Integer> bytesBA;
    @FXML
    public TableColumn<ConversationModel, Double> duration;
    @FXML
    public TableColumn<ConversationModel, Double> bpsAB;
    @FXML
    public TableColumn<ConversationModel, Double> bpsBA;

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

    @FXML
    public void setData(ObservableList<ConversationModel> convModel) {
        tableView.setItems(convModel);
    }
}
