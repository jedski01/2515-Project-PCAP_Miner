package pcap_analyzer;

import conversation.ConversationModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jed on 2017-04-02.
 */
public class IPConversationController extends ConversationController {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableColumn<ConversationModel, Integer> minTTL;

    @FXML
    private TableColumn<ConversationModel, Integer> maxTTL;

    @FXML
    private TableColumn<ConversationModel, Integer> avgTTL;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        super.initialize(location, resources);

        minTTL.setCellValueFactory(cellData -> cellData.getValue().minTTLProperty().asObject());
        maxTTL.setCellValueFactory(cellData -> cellData.getValue().maxTTLProperty().asObject());
        avgTTL.setCellValueFactory(cellData -> cellData.getValue().avgTTLProperty().asObject());

    }





}
