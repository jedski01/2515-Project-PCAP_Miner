package pcap_analyzer;

import conversation.ConversationModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import pcap_packets.PCapInterface;
import pcap_packets.PacketStat;
import pcap_packets.Protocol;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * Created by Jed on 2017-04-02.
 */
public class MainSceneController implements Initializable, ControlledScreen{

    private enum PieChartNames{
        IP, TRANSPORT, RETRANSMISSION
    }

    private static class WindowAttributes {
        Stage stage;
        ConversationController controller;
        String title;
        String fxmlFile;

        public WindowAttributes(Stage stage, ConversationController controller, String title, String fxmlFile) {
            this.stage = stage;
            this.controller = controller;
            this.title = title;
            this.fxmlFile = fxmlFile;
        }
    }


    private ShareableData shareableData = ShareableData.getInstance();
    private HashMap<PieChartNames, ArrayList<DataItem>> statDataItems = new HashMap<>();

    private static HashMap<Protocol, WindowAttributes> windows = new HashMap<>();
    static {

        WindowAttributes ethWindowAttr = new WindowAttributes(new Stage(), null,
                "Ethernet Conversations", "DefaultConversation.fxml");

        WindowAttributes ip4WindowAttr = new WindowAttributes(new Stage(), null,
                "IPv4 Conversations", "IPv4Conversation.fxml");

        WindowAttributes ip6WindowAttr = new WindowAttributes(new Stage(), null,
                "IPv6 Conversations", "IPv6Conversation.fxml");

        WindowAttributes tcpWindowAttr = new WindowAttributes(new Stage(), null,
                "TCP Conversations", "TCPConversation.fxml");

        WindowAttributes udpWindowAttr = new WindowAttributes(new Stage(), null,
                "UDP Conversations", "UDPConversation.fxml");

        windows.put(Protocol.ETHERNET, ethWindowAttr);
        windows.put(Protocol.IPV4, ip4WindowAttr);
        windows.put(Protocol.IPV6, ip6WindowAttr);
        windows.put(Protocol.TCP, tcpWindowAttr);
        windows.put(Protocol.UDP, udpWindowAttr);
    }


    @FXML
    private AnchorPane rootPane;
    @FXML
    private Label lblFilename;
    @FXML
    private Label lblPacketCount;

    @FXML
    private Label lblPacketsPerSec;

    @FXML
    private Label lblAveragePacketLength;

    @FXML
    private Label lblRetransmission;

    @FXML
    private ToggleButton btnTransport;

    @FXML
    private ToggleButton btnIp;

    @FXML
    private ToggleButton btnRetransmission;

    @FXML
    private RadioButton rdoPie;

    @FXML
    private RadioButton rdoBar;

    @FXML
    private Pane replaceablePane;

    @FXML
    private ToggleGroup dataButtonGroup;

    @FXML
    private ToggleGroup chartToggleGroup;


    ScreensController myController;

    @FXML
    public void handleOpenFileAction() {
        FileChooser fileChooser = new FileChooser();

        //apply extension filters for PCAP files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PCAP Files", "*.pcap", "*.pcapng"));

        //set title for the stage
        fileChooser.setTitle("Open PCAP file");
        File pcapFile = fileChooser.showOpenDialog(new Stage());

        if (pcapFile == null) {
            return;
        }
        //get filename of selected file so to use loadFromFile method from the PCapInterface
        String inpFileName = pcapFile.getAbsolutePath();
        String fname = pcapFile.getName();


        PcapHandle handle = null;
        try {
            handle = PCapInterface.openPcapFile(inpFileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error! Cannot read file");
        }
        try {
            PCapInterface.processPcapFile(handle);
        } catch (PcapNativeException e) {
            //e.printStackTrace();
        } catch (NotOpenException e) {
            //e.printStackTrace();
        }

        PCapInterface.updateAllConversationModels();
        setLabelValues(fname, PCapInterface.packetStat);
        setPieChartDataItems();

        if(dataButtonGroup.getSelectedToggle().equals(btnTransport)) {
            shareableData.setDistributionData(statDataItems.get(PieChartNames.TRANSPORT));
        } else if(dataButtonGroup.getSelectedToggle().equals(btnIp)) {
            shareableData.setDistributionData(statDataItems.get(PieChartNames.IP));
        } else if(dataButtonGroup.getSelectedToggle().equals(btnRetransmission)) {
            shareableData.setDistributionData(statDataItems.get(PieChartNames.RETRANSMISSION));
        }

        if(chartToggleGroup.getSelectedToggle().equals(rdoPie)) {
            myController.setScreen(Main.pieChartScreen, replaceablePane);
        } else {
            myController.setScreen(Main.barChartScreen, replaceablePane);
        }

        //update table here
        //((ConversationController)conversationController.get(Protocol.ETHERNET)).setData();
        updateTables();

    }

    private void updateTables() {

        for (Protocol protocol : Protocol.values()) {
            WindowAttributes window = windows.get(protocol);

            ObservableList<ConversationModel> cm =
                    FXCollections.observableArrayList(PCapInterface.getConversationModel(protocol));
            window.controller.setData(cm);
            window.stage.setTitle(String.format("%s (%d)", window.title, cm.size()));
        }
    }

    @FXML
    public void handleTransportButtonAction() {

        shareableData.setDistributionData(statDataItems.get(PieChartNames.TRANSPORT));
        if(chartToggleGroup.getSelectedToggle().equals(rdoPie)) {
            myController.setScreen(Main.pieChartScreen, replaceablePane);
        } else {
            myController.setScreen(Main.barChartScreen, replaceablePane);
        }
    }

    @FXML
    public void handleIPButtonAction() {
        shareableData.setDistributionData(statDataItems.get(PieChartNames.IP));
        if(chartToggleGroup.getSelectedToggle().equals(rdoPie)) {
            myController.setScreen(Main.pieChartScreen, replaceablePane);
        } else {
            myController.setScreen(Main.barChartScreen, replaceablePane);
        }
    }

    @FXML
    public void handleRetransmissionButtonAction() {
        shareableData.setDistributionData(statDataItems.get(PieChartNames.RETRANSMISSION));
        if(chartToggleGroup.getSelectedToggle().equals(rdoPie)) {
            myController.setScreen(Main.pieChartScreen, replaceablePane);
        } else {
            myController.setScreen(Main.barChartScreen, replaceablePane);
        }
    }

    @FXML
    public void handlePieChartRadioAction() {
        myController.setScreen(Main.pieChartScreen, replaceablePane);
    }

    @FXML
    public void handleBarChartRadioAction() {
        myController.setScreen(Main.barChartScreen, replaceablePane);
    }

    @FXML
    private void handleEthernetMenuItemAction() {
        showConversationWindow(Protocol.ETHERNET);
    }

    @FXML
    private void handleIPv4MenuItemAction() {
        showConversationWindow(Protocol.IPV4);
    }

    @FXML
    private void handleIPv6MenuItemAction() {
        showConversationWindow(Protocol.IPV6);
    }

    @FXML
    private void handleTCPMenuItemAction() {
        showConversationWindow(Protocol.TCP);
    }

    @FXML
    private void handleUDPMenuItemAction() {
        showConversationWindow(Protocol.UDP);
    }

    @FXML
    public void handleCloseAction() {

        for (Protocol protocol : Protocol.values()) {
            windows.get(protocol).controller.handleCloseAction();
        }
        ((Stage)rootPane.getScene().getWindow()).close();
    }
    //VALUE SETTINGS TO BE USED BY PIE CHART AND LABELS
    private void setLabelValues(String filename, PacketStat stat) {
        lblFilename.setText("File: "+filename);
        lblAveragePacketLength.setText(String.format("%d", stat.getAvgPacketSize()));
        lblPacketCount.setText(String.format("%d", stat.getPacketCount()));
        lblPacketsPerSec.setText(String.format("%d",stat.getPacketPerSec()));
        lblRetransmission.setText(String.format("%d",stat.getPacketLost()));
    }

    private void setPieChartDataItems() {
        //TCP and UDP
        statDataItems.put(PieChartNames.TRANSPORT, setTCPUDPDataItems());
        statDataItems.put(PieChartNames.IP, setIPV4IPV6DataItems());
        statDataItems.put(PieChartNames.RETRANSMISSION, setTransmissionDataItems());
    }

    private ArrayList<DataItem> setTCPUDPDataItems() {

        ArrayList<DataItem> result = new ArrayList<>();
        DataItem udp = new DataItem("UDP", PCapInterface.packetStat.getUdpCount());
        DataItem tcp = new DataItem("TCP", PCapInterface.packetStat.getTcpCount());
        result.add(udp);
        result.add(tcp);
        return result;

    }

    private ArrayList<DataItem> setIPV4IPV6DataItems() {
        ArrayList<DataItem> result = new ArrayList<>();
        DataItem ipv4 = new DataItem("IPv4", PCapInterface.packetStat.getIpv4Count());
        DataItem ipv6 = new DataItem("IPv6", PCapInterface.packetStat.getIpv6Count());
        result.add(ipv4);
        result.add(ipv6);
        return result;
    }

    private ArrayList<DataItem>  setTransmissionDataItems () {
        ArrayList<DataItem> result = new ArrayList<>();
        DataItem received = new DataItem("Received",
                PCapInterface.packetStat.getPacketCount()-PCapInterface.packetStat.getPacketLost());
        DataItem lost = new DataItem("Lost", PCapInterface.packetStat.getPacketLost());
        result.add(received);
        result.add(lost);
        return result;
    }

    private void showConversationWindow(Protocol protocol) {
        Stage stage = windows.get(protocol).stage;
        if(stage.isShowing()) {
            stage.requestFocus();
        } else {
            stage.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (PieChartNames name : PieChartNames.values()) {
            statDataItems.put(name, new ArrayList<DataItem>());
        }

        for (Protocol protocol : Protocol.values()) {
            WindowAttributes window= windows.get(protocol);
            FXMLLoader newFXMLLoader = null;
            Parent newRoot = null;
            try
            {
                newFXMLLoader = new FXMLLoader(getClass().getResource(window.fxmlFile));
                newRoot = (Parent) newFXMLLoader.load();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            window.stage.setTitle(window.title);
            window.stage.setScene(new Scene(newRoot));
            window.controller = newFXMLLoader.getController();
        }
    }

    @Override
    public void setScreenParent(ScreensController screenPage) {
        myController = screenPage;
    }

    @Override
    public void onSetScreen() {

    }
}
