package pcap_analyzer;

import conversation.ConversationModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jed on 2017-04-02.
 */
public class IPConversationController extends ConversationController {

    @FXML
    public TableColumn<ConversationModel, Integer> minTTL;

    @FXML
    public TableColumn<ConversationModel, Integer> maxTTL;

    @FXML
    public TableColumn<ConversationModel, Integer> avgTTL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        super.initialize(location, resources);

        minTTL.setCellValueFactory(cellData -> cellData.getValue().minTTLProperty().asObject());
        maxTTL.setCellValueFactory(cellData -> cellData.getValue().maxTTLProperty().asObject());
        avgTTL.setCellValueFactory(cellData -> cellData.getValue().avgTTLProperty().asObject());

    }





}
