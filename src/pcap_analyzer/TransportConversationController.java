package pcap_analyzer;

import conversation.ConversationModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jed on 2017-04-02.
 */
public class TransportConversationController  extends ConversationController{

    @FXML
    public TableColumn<ConversationModel, Integer> portA;
    @FXML
    public TableColumn<ConversationModel, Integer> portB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        super.initialize(location, resources);
        portA.setCellValueFactory(cellData -> cellData.getValue().portAProperty().asObject());
        portB.setCellValueFactory(cellData -> cellData.getValue().portBProperty().asObject());
    }
}
