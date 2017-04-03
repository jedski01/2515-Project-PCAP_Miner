package pcap_analyzer;

import conversation.ConversationModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jed on 2017-04-02.
 */
public class TCPConversationController extends TransportConversationController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableColumn<ConversationModel, Integer> retransmitAB;
    @FXML
    private TableColumn<ConversationModel, Integer> retransmitBA;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        super.initialize(location, resources);
        retransmitAB.setCellValueFactory(cellData -> cellData.getValue().retAToBProperty().asObject());
        retransmitBA.setCellValueFactory(cellData -> cellData.getValue().retBToAProperty().asObject());
    }
}
